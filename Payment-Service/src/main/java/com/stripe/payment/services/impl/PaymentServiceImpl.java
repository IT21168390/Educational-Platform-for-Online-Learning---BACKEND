package com.stripe.payment.services.impl;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.payment.consts.EnrollmentStatus;
import com.stripe.payment.dao.EnrollmentDAO;
import com.stripe.payment.services.PaymentService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.post;

@RestController
@RequestMapping("/payment")
@Service
@EnableDiscoveryClient
public class PaymentServiceImpl implements PaymentService {
    private static Gson gson = new Gson();
    private static WebClient webClient;

    public PaymentServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public static void main(String[] args) {
//        port(4242);

        Dotenv dotenv = Dotenv.load();

        checkEnv();

        Stripe.apiKey = dotenv.get("STRIPE_SECRET_KEY");


        staticFiles.externalLocation(
                Paths.get(Paths.get("").toAbsolutePath().toString(), dotenv.get("STATIC_DIR")).normalize().toString());

        get("/config", (request, response) -> {
            response.type("application/json");
            Price price = Price.retrieve(dotenv.get("PRICE"));

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("publicKey", dotenv.get("STRIPE_PUBLISHABLE_KEY"));
            responseData.put("unitAmount", price.getUnitAmount());
            responseData.put("currency", price.getCurrency());
            return gson.toJson(responseData);
        });

        // Fetch the Checkout Session to display the JSON result on the success page
        get("/checkout-session", (request, response) -> {
            response.type("application/json");

            String sessionId = request.queryParams("sessionId");
            Session session = Session.retrieve(sessionId);

            return gson.toJson(session);
        });

        post("/create-checkout-session", (request, response) -> {
            String domainUrl = dotenv.get("DOMAIN");
            Long quantity = Long.parseLong(request.queryParams("quantity"));
            String price = dotenv.get("PRICE");

            SessionCreateParams.Builder builder = new SessionCreateParams.Builder()
                    .setSuccessUrl(domainUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(domainUrl + "/canceled.html")
                    .setMode(SessionCreateParams.Mode.PAYMENT);

            // Add a line item for the sticker the Customer is purchasing
            SessionCreateParams.LineItem item = new SessionCreateParams.LineItem.Builder().setQuantity(quantity).setPrice(price).build();
            builder.addLineItem(item);

            SessionCreateParams createParams = builder.build();
            Session session = Session.create(createParams);

            response.redirect(session.getUrl(), 303);
            return "";
        });

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            String endpointSecret = dotenv.get("STRIPE_WEBHOOK_SECRET");

            Event event = null;

            try {
                event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            } catch (SignatureVerificationException e) {
                // Invalid signature
                response.status(400);
                return "";
            }

            switch (event.getType()) {
                case "checkout.session.completed":
                    updateEnrollmentStatus();
                    System.out.println("Payment succeeded!");
                    response.status(200);
                    return "";
                default:
                    response.status(200);
                    return "";
            }
        });
    }

    public static void checkEnv() {
        Dotenv dotenv = Dotenv.load();
        String price = dotenv.get("PRICE");
        if(price == "price_12345" || price == "" || price == null) {
            System.out.println("You must set a Price ID in the .env file. Please see the README.");
            System.exit(0);
        }
    }


    private static void updateEnrollmentStatus() {
//        webClient = WebClient.create("http://localhost:8060"); // Base URL of your Administrative-service
        approveEnrollment("663b3ee1299818e0be02a8a1", EnrollmentStatus.PAID);
    }

    public static Mono<EnrollmentDAO> approveEnrollment(String enrollmentId, EnrollmentStatus status) {
        return webClient.put()
                .uri("http://localhost:8060/admin/enrollments/{enrollmentId}/status", enrollmentId)
                .bodyValue(status)
                .retrieve()
                .bodyToMono(EnrollmentDAO.class);
    }

}
