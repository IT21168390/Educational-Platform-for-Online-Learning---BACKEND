package com.stripe.PaymentService.daos;

import com.stripe.model.Price;
import com.stripe.model.Product;

import java.math.BigDecimal;

public class ProductDAO {

    static Product[] products;

    static {
        products = new Product[1];

        Product sampleProduct = new Product();
        Price samplePrice = new Price();

        sampleProduct.setName("Course");
        sampleProduct.setId("course");
        samplePrice.setCurrency("usd");
        samplePrice.setUnitAmountDecimal(BigDecimal.valueOf(5000));
        sampleProduct.setDefaultPriceObject(samplePrice);
        products[0] = sampleProduct;
    }

    public static Product getProduct(String id) {

        if ("shoe".equals(id)) {
            return products[0];
        }  else return new Product();

    }
}