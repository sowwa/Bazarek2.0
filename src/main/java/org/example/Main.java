package org.example;

import common.models.Beverages.Beer;
import common.models.Beverages.BeveragePack;
import common.models.Discounts.Discount;
import common.models.Discounts.FixedPriceDiscount;
import common.models.Shop.Cart;
import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //todo: get discounts form file or hardcode
        //todo: get carts/receipt from file or hardcode
        //todo: apply discounts (maybe get products, add to cart, check discounts, make checkout print receipts)
        //todo: print receipt
        //testing area
        BigDecimal price = new BigDecimal(22);
        var b1 = new Beer("A", price);
        var bP1 = new BeveragePack<Beer>("6pack", price,6, b1);

        var fD1 = new FixedPriceDiscount(new ArrayList<>(){{add(b1.id);}}, Unit.Piece, 6);
        var fD1Pack = new FixedPriceDiscount(new ArrayList<>(){{add(bP1.id);}}, Unit.Piece, 1);

        var discountList = new ArrayList<Discount>();
        discountList.add(fD1);
        discountList.add(fD1Pack);

        var d1 = discountList.stream().filter(d -> d.productsIds.contains(b1.id)).findFirst().orElse(null);

        var cart = new Cart();
        var cP1 = new CartProduct(b1, 6, null);
        //or adding pack as 6 separate beers to the cart
        var cP2 = new CartProduct(bP1, 1, null);

        cart.addToCart(cP1);
        cart.addToCart(cP2);

        var productsInCartCount = cart.cartProducts.size();

        //assert d1 != null;
        System.out.println("Cart items: "+ productsInCartCount + " product Id " + bP1.id + " \n discout for beer " + d1.id);

        var console = System.console();
        if(console != null){
            console.writer().println("aaaaaaa");
        }
    }
}