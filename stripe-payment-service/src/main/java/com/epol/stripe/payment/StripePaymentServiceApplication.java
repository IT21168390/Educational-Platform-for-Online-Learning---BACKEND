package com.epol.stripe.payment;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import com.google.gson.Gson;

import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.model.Price;
import com.stripe.exception.*;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Service
@EnableDiscoveryClient
@SpringBootApplication
public class StripePaymentServiceApplication {
    private static Gson gson = new Gson();
    /*private static WebClient webClient;

    public Server(WebClient webClient) {
        this.webClient = webClient;
    }*/

    public static void main(String[] args) {
        //port(4242);

         String STRIPE_PUBLISHABLE_KEY="pk_test_51PE8x1LMktVF5ZUl7Bzw5JG9LvzuxeZ91IL5rXjQjricfSm9e7DlaSIx4KpN3dwAX57h0J7LY4gnAZhaTcnRxaFt00QdV4rn1b";
        String STRIPE_SECRET_KEY="sk_test_51PE8x1LMktVF5ZUlnMqjMGolvipIAFxVzSKtQntg12p6cMP7LLpcIbBdBJBkjj24c75jASi1rUB1pL2XuRrGbFgv00chCwbda2";

        String STRIPE_WEBHOOK_SECRET= "whsec_50ec2349f43e26ac0503788ff62ae2f95d6870d994b017e63bae7ef4d42b39ad";

        String STATIC_DIR="./html";

        String PRICE="price_1PEkk4LMktVF5ZUlrgmlojNw";

        String DOMAIN="http://localhost:3000";


        String PAYMENT_METHOD_TYPES="card";



        //Dotenv dotenv = Dotenv.load();

        //checkEnv();

        Stripe.apiKey = STRIPE_SECRET_KEY;


        staticFiles.externalLocation(
                Paths.get(Paths.get("").toAbsolutePath().toString(), STATIC_DIR).normalize().toString());

        get("/config", (request, response) -> {
            response.type("application/json");
            Price price = Price.retrieve(PRICE);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("publicKey", STRIPE_PUBLISHABLE_KEY);
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
            String domainUrl = DOMAIN;
            Long quantity = Long.parseLong(request.queryParams("quantity"));
            String price = PRICE;

            SessionCreateParams.Builder builder = new SessionCreateParams.Builder()
                    .setSuccessUrl(domainUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(domainUrl + "/canceled.html")
                    .setMode(SessionCreateParams.Mode.PAYMENT);

            // Add a line item for the sticker the Customer is purchasing
            LineItem item = new LineItem.Builder().setQuantity(quantity).setPrice(price).build();
            builder.addLineItem(item);

            SessionCreateParams createParams = builder.build();
            Session session = Session.create(createParams);

            response.redirect(session.getUrl(), 303);
            return "";
        });

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            String endpointSecret = STRIPE_WEBHOOK_SECRET;

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
                    //updateEnrollmentStatus();
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




    /*public static Mono<EnrollmentDAO> approveEnrollment(String enrollmentId, EnrollmentStatus status) {
        return webClient.put()
                .uri("http://localhost:8060/admin/enrollments/{enrollmentId}/status", enrollmentId)
                .bodyValue(status)
                .retrieve()
                .bodyToMono(EnrollmentDAO.class);
    }*/

}
