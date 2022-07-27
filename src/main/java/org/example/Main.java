package org.example;

import common.models.Product;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal price = new BigDecimal(22);
        var p1 = new Product("test", price);

        System.out.println("Hello world!"+ p1.Name+p1.Price.toString());
        var console = System.console();
        if(console != null){
            console.writer().println("aaaaaaa");
        }
    }
}