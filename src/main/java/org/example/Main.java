package org.example;

import common.models.Beverages.Beer;
import common.models.Discounts.Discount;
import common.models.Product;
import common.models.Shop.Cart;
import common.models.Shop.CartProduct;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        //todo: get discounts form file
        //todo: get carts/receipt from file
        //todo: apply discounts (maybe get products, add to cart, check discounts, make checkout print receipts)
        //todo: print receipt
        //testing area
        BigDecimal price = new BigDecimal(22);
        var b1 = new Beer("A", price);
        var cart = new Cart();
        var cartProduct = new CartProduct(b1, 6, null);
        cart.AddToCart(cartProduct);

        var productsInCartCount = cart.CartProducts.size();
        var p1 = new Beer("test", price);

        System.out.println("Cart items: "+ productsInCartCount);
        var console = System.console();
        if(console != null){
            console.writer().println("aaaaaaa");
        }
    }
}