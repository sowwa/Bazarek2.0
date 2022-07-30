package org.main;

import common.models.products.beverages.Beer;
import common.models.discounts.FixedPriceDiscount;
import common.models.shop.Order;
import common.models.shop.OrderProduct;
import common.models.enums.Unit;
import common.models.shop.OrderProductDiscount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        //todo: get discounts form file or hardcode
        //todo: get carts/receipt from file or hardcode
        //todo: apply discounts (maybe get products, add to cart, check discounts, make checkout print receipts)
        //todo: print receipt
        //presentation setup
        var cart = new Order();


        //1.1: test beer discount - separate beers
        var belgBeer = new Beer("Belgium", new BigDecimal(2),new Date());
        var belgBeer2 = new Beer("22Belgium", new BigDecimal(1),new Date());
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<Integer>(){{add(belgBeer.id);add(belgBeer2.id);}}, Unit.Piece, 6, new BigDecimal(3));
        var belgBeerCPD = new OrderProductDiscount(belgBeerDiscount, false);
        var bCP = new OrderProduct(belgBeer, 7, belgBeerCPD);
        var bCP2 = new OrderProduct(belgBeer2, 6, belgBeerCPD);

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
      /*  var cartB = new Cart();

        var bread = new Bread("Belgium", new BigDecimal(10), new Date());
        var bread2 = new Bread("22Belgium", new BigDecimal(3), new Date());
        var breadDiscount = new XForYDiscount(new ArrayList<Integer>(){{add(bread.id);add(bread2.id);}},
                Unit.Piece, 2, 1);
        var breadCPD = new CartProductDiscount(breadDiscount, false);
        var breadCP = new CartProduct(bread, 1, breadCPD);
        var breadCP2 = new CartProduct(bread2, 1, breadCPD);

        cartB.addToCart(breadCP);
        cartB.addToCart(breadCP2);
        cartB.checkForDiscounts(breadCP2);
        var e2 = (XForYDiscount) cartB.cartProducts.get(0).discount.discount;
        System.out.println("\n\n\nCart items: "+ cartB.cartProducts.size()
                + "\nproduct name: " + cartB.cartProducts.get(0).product.name
                + "\nproduct price: " + cartB.cartProducts.get(0).product.price
                //+ "\ndiscout for beer: " + e2.fixedPrice
                + "\nprice: "+ cartB.cartProducts.get(0).price.add(cartB.cartProducts.get(1).price)
                + "\ndiscount value: " + cartB.cartProducts.get(0).discountValue.add(cartB.cartProducts.get(1).discountValue)
        );*/

        //2.2: test bread discount - 3for1

        //2.3: test bread discount - 2for1 and 3for1

        //3.1: test veggie discount - 0-100g

      /*  var cartV = new Cart();

        var vegg = new Vegetable("Belgium", new BigDecimal(10), new Date(), 110);
        var vegg2 = new Vegetable("22Belgium", new BigDecimal(20), new Date(), 50);
        var vegg2q = new Vegetable("qqq", new BigDecimal(100), new Date(), 30);
        var veggDiscount = new PercentageDiscount(new ArrayList<Integer>(){{add(vegg.id);add(vegg2.id);}},
                Unit.Piece, 100 , 200, 10);
        var veggCPD = new CartProductDiscount(veggDiscount, false);
        var veggCP = new CartProduct(vegg, 1, veggCPD);
        var veggCP2 = new CartProduct(vegg2, 1, veggCPD);
        var veggCP233 = new CartProduct(vegg2q, 1, null);

        cartV.addToCart(veggCP);
        cartV.addToCart(veggCP2);
        cartV.addToCart(veggCP233);
        cartV.checkForDiscounts(veggCP2);
        var e3 = (PercentageDiscount) cartV.cartProducts.get(0).discount.discount;
        System.out.println("\n\n\nCart items: "+ cartV.cartProducts.size()
                + "\nproduct name: " + cartV.cartProducts.get(0).product.name
                + "\nproduct price: " + cartV.cartProducts.get(0).product.price
                //+ "\ndiscout for beer: " + e2.fixedPrice
                + "\nprice: "+ cartV.cartProducts.get(0).price.add(cartV.cartProducts.get(1).price).add(cartV.cartProducts.get(2).price)
                + "\ndiscount value: " + cartV.cartProducts.get(0).discountValue.add(cartV.cartProducts.get(1).discountValue).add(cartV.cartProducts.get(2).discountValue)
        );*/


        //3.2: test veggie discount - 100-500g

        //3.2: test veggie discount - 500g<



        var console = System.console();
        if(console != null){
            console.writer().println("aaaaaaa");
        }
    }
}