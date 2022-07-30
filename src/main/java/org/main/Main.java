package org.main;

import common.models.discounts.PercentageDiscount;
import common.models.discounts.XForYDiscount;
import common.models.products.beverages.Beer;
import common.models.discounts.FixedPriceDiscount;
import common.models.products.food.Bread;
import common.models.products.food.Vegetable;
import common.models.shop.Order;
import common.models.shop.OrderProduct;
import common.models.enums.Unit;
import common.models.shop.OrderProductDiscount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //todo: get discounts form file or hardcode
        //todo: get carts/receipt from file or hardcode
        //todo: apply discounts (maybe get products, add to cart, check discounts, make checkout print receipts)
        //todo: print receipt
        //presentation setup
        var cart = new Order();


        //1.1: test beer discount - separate beers
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeer2 = new Beer("22Belgium", new BigDecimal(5), LocalDate.now());
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<Integer>(){{add(belgBeer.id);add(belgBeer2.id);}}, Unit.Piece, 6, new BigDecimal(1));
        var belgBeerCPD = new OrderProductDiscount(belgBeerDiscount, false);
        var bCP = new OrderProduct(belgBeer, 4, belgBeerCPD);
        var bCP2 = new OrderProduct(belgBeer2, 4, belgBeerCPD);

        cart.addToCart(bCP);
        cart.addToCart(bCP2);
        cart.checkForDiscounts(bCP);
        var e = (FixedPriceDiscount) cart.orderProducts.get(0).discount.discount;
        System.out.println("\n\n\nCart items: "+ cart.orderProducts.size()
                + "\nproduct name: " + cart.orderProducts.get(0).product.name
                + "\nproduct price: " + cart.orderProducts.get(0).product.price
                + "\ndiscout for beer: " + e.fixedPrice
                + "\nprice: "+ cart.orderProducts.get(0).price.add(cart.orderProducts.get(1).price)
                + "\ndiscount value: " + cart.orderProducts.get(0).discountValue.add(cart.orderProducts.get(1).discountValue)
        );

        //1.2: test beer discount - pack of beer

        //1.3: test beer discount - separate beers + pack of beer

        //2.1: test bread discount - 2for1
        var cartB = new Order();
var ff = LocalDate.now();
        var bread = new Bread("Bread", new BigDecimal(1), LocalDate.of(2022, 07, 28), 22);
        var bread2 = new Bread("22Belgium", new BigDecimal(3), LocalDate.of(2022, 07, 28), 22);
        var bread3 = new Bread("aaaa22Belgium", new BigDecimal(3), LocalDate.of(2022, 07, 28), 22);
        var breadDiscount = new XForYDiscount(new ArrayList<Integer>(){{add(bread.id);add(bread2.id);add(bread3.id);}},
                Unit.Piece, 3, 2, 2);
        var breadCPD = new OrderProductDiscount(breadDiscount, false);
        var breadCP = new OrderProduct(bread, 1, breadCPD);
        var breadCP2 = new OrderProduct(bread2, 5, breadCPD);
        var breadCP3 = new OrderProduct(bread3, 1, breadCPD);

        cartB.addToCart(breadCP);
        cartB.addToCart(breadCP2);
        cartB.checkForDiscounts(breadCP2);
        var e2 = (XForYDiscount) cartB.orderProducts.get(0).discount.discount;
        System.out.println("\n\n\nCart items: "+ cartB.orderProducts.size()
                + "\nproduct name: " + cartB.orderProducts.get(0).product.name
                + "\nproduct price: " + cartB.orderProducts.get(0).product.price
                //+ "\ndiscout for beer: " + e2.fixedPrice
                + "\nprice: "+ cartB.orderProducts.get(0).price.add(cartB.orderProducts.get(1).price)
                + "\ndiscount value: " + cartB.orderProducts.get(0).discountValue.add(cartB.orderProducts.get(1).discountValue)
        );

        //2.2: test bread discount - 3for1

        //2.3: test bread discount - 2for1 and 3for1

        //3.1: test veggie discount - 0-100g

       var cartV = new Order();

        var vegg = new Vegetable("Veggies", new BigDecimal(10), LocalDate.now(), 110);
        var vegg2 = new Vegetable("22Belgium", new BigDecimal(20), LocalDate.now(), 50);
        var vegg2q = new Vegetable("qqq", new BigDecimal(100), LocalDate.now(), 30);
        var veggDiscount = new PercentageDiscount(new ArrayList<Integer>(){{add(vegg.id);add(vegg2.id);}},
                Unit.Piece, 100 , 200, 10);
        var veggCPD = new OrderProductDiscount(veggDiscount, false);
        var veggCP = new OrderProduct(vegg, 1, veggCPD);
        var veggCP2 = new OrderProduct(vegg2, 1, veggCPD);
        var veggCP233 = new OrderProduct(vegg2q, 1, veggCPD);

        cartV.addToCart(veggCP);
        cartV.addToCart(veggCP2);
        cartV.addToCart(veggCP233);
        cartV.checkForDiscounts(veggCP2);
        var e3 = (PercentageDiscount) cartV.orderProducts.get(0).discount.discount;
        System.out.println("\n\n\nCart items: "+ cartV.orderProducts.size()
                + "\nproduct name: " + cartV.orderProducts.get(0).product.name
                + "\nproduct price: " + cartV.orderProducts.get(0).product.price
                //+ "\ndiscout for beer: " + e2.fixedPrice
                + "\nprice: "+ cartV.orderProducts.get(0).price.add(cartV.orderProducts.get(1).price).add(cartV.orderProducts.get(2).price)
                + "\ndiscount value: " + cartV.orderProducts.get(0).discountValue.add(cartV.orderProducts.get(1).discountValue).add(cartV.orderProducts.get(2).discountValue)
        );


        //3.2: test veggie discount - 100-500g

        //3.2: test veggie discount - 500g<



        var console = System.console();
        if(console != null){
            console.writer().println("aaaaaaa");
        }
    }
}