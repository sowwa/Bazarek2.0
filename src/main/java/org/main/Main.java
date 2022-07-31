package org.main;

import common.models.discounts.PercentageDiscount;
import common.models.discounts.XForYDiscount;
import common.models.products.beverages.Beer;
import common.models.discounts.FixedPriceDiscount;
import common.models.products.beverages.BeveragePack;
import common.models.products.food.Bread;
import common.models.products.food.Vegetable;
import common.models.order.Order;
import proccessing.DiscountService;
import proccessing.OrderService;
import proccessing.ReceiptService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //todo: get carts/receipt from file or hardcode
        //todo: apply discounts (maybe get products, add to cart, check discounts, make checkout print receipts)
        //todo: make three orders

        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var dutchBeer = new Beer("Dutch", new BigDecimal(5), LocalDate.now());
        var germanBeer = new Beer("German", new BigDecimal(5), LocalDate.now());

        var belgBeerPack = new BeveragePack<Beer>("Belgium Six Pack", new BigDecimal(12),6, belgBeer, LocalDate.now());
        var dutchBeerPack = new BeveragePack<Beer>("Dutch Six Pack", new BigDecimal(12), 6, dutchBeer, LocalDate.now());
        var germanBeerPack = new BeveragePack<Beer>("German Six Pack", new BigDecimal(12), 6, germanBeer, LocalDate.now());

        var whiteBread = new Bread("White", new BigDecimal(1.56), LocalDate.of(2022, 07, 28));
        var fullGrainBread = new Bread("Full Grain", new BigDecimal(3), LocalDate.of(2022, 07, 28));
        var glutenFreeBread = new Bread("Gluten Free", new BigDecimal(3), LocalDate.of(2022, 07, 25));

        var carrot = new Vegetable("Carrot", new BigDecimal(0.67), LocalDate.now());
        var broccoli = new Vegetable("Brocolli", new BigDecimal(2), LocalDate.now());
        var onion = new Vegetable("Onion", new BigDecimal(1), LocalDate.now());

        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());add(fullGrainBread.getId());add(glutenFreeBread.getId());}},
                2, 1, 3, "2for1");
        var threeForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());add(fullGrainBread.getId());add(glutenFreeBread.getId());}},
                 3, 1, 6, "3for1");
        var belgGermanBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());add(germanBeer.getId());}},
                6, new BigDecimal(3), "Belgian and German Beer 6 for 3");
        var dutchBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(dutchBeer.getId());}},
                6, new BigDecimal(2), "Dutch Beer 6 for 2");
        var belgGermanBeerPackDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeerPack.getId());add(germanBeerPack.getId());}},
                1, new BigDecimal(3), "Belgian and German Beer 6 for 3");
        var dutchBeerPackDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(dutchBeerPack.getId());}},
                1, new BigDecimal(2), "Dutch Beer 6 for 3");
        var veggie0To100Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());add(broccoli.getId());add(onion.getId());}},
                0 , 100, 5, "Vegetables 0-100");
        var veggie100To500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());add(broccoli.getId());add(onion.getId());}},
                100 , 500, 7, "Vegetables 100-500");
        var veggieOver500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());add(broccoli.getId());add(onion.getId());}},
                 500 , null, 10, "Vegetables over 500");

        var order1 = new Order();

        order1.addProduct(belgBeer, 7);
        order1.addProduct(germanBeer, 6);
        order1.addProduct(carrot, 12);
        order1.addProduct(dutchBeerPack, 1);
        order1.addProduct(dutchBeer, 3);
        order1.addProduct(whiteBread,1);
        order1.addProduct(fullGrainBread,3);
        order1.addProduct(glutenFreeBread,2);
        order1.addProduct(onion, 2137);
        //order1.addProduct(belgBeer, 7);

        var receiptService = new ReceiptService();
        var discountService = new DiscountService();
        discountService.addDiscount(belgGermanBeerDiscount);
        discountService.addDiscount(dutchBeerPackDiscount);
        discountService.addDiscount(veggie0To100Discount);
        discountService.addDiscount(twoForOneDiscount);
        discountService.addDiscount(veggieOver500Discount);

        var orderService = new OrderService(receiptService, discountService);

        orderService.addOrder(order1);
        var receiptOfOrder1 = orderService.processOrder(1);

        receiptOfOrder1.print();

        //1.1: test beer discount - separate beers

        //1.2: test beer discount - pack of beer

        //1.3: test beer discount - separate beers + pack of beer

        //2.1: test bread discount - 2for1

        //2.2: test bread discount - 3for1

        //2.3: test bread discount - 2for1 and 3for1

        //3.1: test veggie discount - 0-100g

        //3.2: test veggie discount - 100-500g

        //3.2: test veggie discount - 500g<

        var console = System.console();
        if(console != null){
            console.writer().println("aaaaaaa");
        }
    }
}