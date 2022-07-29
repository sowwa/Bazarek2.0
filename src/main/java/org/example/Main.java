package org.example;

import common.models.beverages.Beer;
import common.models.discounts.FixedPriceDiscount;
import common.models.discounts.PercentageDiscount;
import common.models.discounts.XForYDiscount;
import common.models.food.Bread;
import common.models.food.Vegetable;
import common.models.shop.Cart;
import common.models.shop.CartProduct;
import common.models.enums.Unit;
import common.models.shop.CartProductDiscount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        //todo: get discounts form file or hardcode
        //todo: get carts/receipt from file or hardcode
        //todo: apply discounts (maybe get products, add to cart, check discounts, make checkout print receipts)
        //todo: print receipt
        //testing area
      //  BigDecimal price = new BigDecimal(22);
        //var b1 = new Beer("A", price);
        //var bP1 = new BeveragePack<Beer>("6pack", price,6, b1);

      ///  var fD1 = new FixedPriceDiscount(new ArrayList<>(){{add(b1.id);}}, Unit.Piece, 6);
     //   var fD1Pack = new FixedPriceDiscount(new ArrayList<>(){{add(bP1.id);}}, Unit.Piece, 1);

    //    var discountList = new ArrayList<Discount>();
     //   discountList.add(fD1);
     //   discountList.add(fD1Pack);

    //    var d1 = discountList.stream().filter(d -> d.productsIds.contains(b1.id)).findFirst().orElse(null);

     //   var cart1 = new Cart();
     //   var cP1 = new CartProduct(b1, 6, null);
        //or adding pack as 6 separate beers to the cart
    //    var cP2 = new CartProduct(bP1, 1, null);

     //   cart1.addToCart(cP1);
     //   cart1.addToCart(cP2);

      //  var productsInCartCount = cart1.cartProducts.size();

        //assert d1 != null;
        //System.out.println("Cart items: "+ productsInCartCount + " product Id " + bP1.id + " \n discout for beer " + d1.id);
        //presentation setup
      /*  var cart = new Cart();


        //1.1: test beer discount - separate beers
        var belgBeer = new Beer("Belgium", new BigDecimal(2));
        var belgBeer2 = new Beer("22Belgium", new BigDecimal(2));
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<Integer>(){{add(belgBeer.id);add(belgBeer2.id);}}, Unit.Piece, 6, new BigDecimal(3));
        var belgBeerCPD = new CartProductDiscount(belgBeerDiscount, false);
        var bCP = new CartProduct(belgBeer, 7, belgBeerCPD);
        var bCP2 = new CartProduct(belgBeer2, 6, belgBeerCPD);

        cart.addToCart(bCP);
        cart.addToCart(bCP2);
        cart.checkForDiscounts(bCP);
        var e = (FixedPriceDiscount) cart.cartProducts.get(0).discount.discount;
        System.out.println("\n\n\nCart items: "+ cart.cartProducts.size()
                + "\nproduct name: " + cart.cartProducts.get(0).product.name
                + "\nproduct price: " + cart.cartProducts.get(0).product.price
                + "\ndiscout for beer: " + e.fixedPrice
                + "\nprice: "+ cart.cartProducts.get(0).price.add(cart.cartProducts.get(1).price)
                + "\ndiscount value: " + cart.cartProducts.get(0).discountValue.add(cart.cartProducts.get(1).discountValue)
        );*/

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

        var cartV = new Cart();

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
        );


        //3.2: test veggie discount - 100-500g

        //3.2: test veggie discount - 500g<



        var console = System.console();
        if(console != null){
            console.writer().println("aaaaaaa");
        }
    }
}