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
        var belgBeer = new Beer("Belgium Beer", new BigDecimal(1), LocalDate.now());
        var dutchBeer = new Beer("Dutch Beer", new BigDecimal(5), LocalDate.now());
        var germanBeer = new Beer("German Beer", new BigDecimal(5), LocalDate.now());

        var belgBeerPack = new BeveragePack<>("Belgium Beer Six Pack", new BigDecimal(12),6, belgBeer, LocalDate.now());
        var dutchBeerPack = new BeveragePack<>("Dutch Beer Six Pack", new BigDecimal(12), 6, dutchBeer, LocalDate.now());
        var germanBeerPack = new BeveragePack<>("German Beer Six Pack", new BigDecimal(12), 6, germanBeer, LocalDate.now());

        var whiteBread = new Bread("White Bread", new BigDecimal("1.56"), LocalDate.now().minusDays(3));
        var fullGrainBread = new Bread("Full Grain Bread", new BigDecimal(3), LocalDate.now().minusDays(3));
        var glutenFreeBread = new Bread("Gluten Free Bread", new BigDecimal(3), LocalDate.now().minusDays(6));
        var sunflowerBread = new Bread("Sunflower seeds Bread", new BigDecimal(3), LocalDate.now().minusDays(1));

        var carrot = new Vegetable("Carrot", new BigDecimal("0.67"), LocalDate.now());
        var broccoli = new Vegetable("Brocolli", new BigDecimal("0.2"), LocalDate.now());
        var onion = new Vegetable("Onion", new BigDecimal("0.1"), LocalDate.now());

        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());add(fullGrainBread.getId());add(glutenFreeBread.getId());add(sunflowerBread.getId());}},
                2, 1, 3, "2for1");
        var threeForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());add(fullGrainBread.getId());add(glutenFreeBread.getId());add(sunflowerBread.getId());}},
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

        var receiptService = new ReceiptService();
        var discountService = new DiscountService();
        discountService.addDiscount(belgGermanBeerDiscount);
        discountService.addDiscount(dutchBeerPackDiscount);
        discountService.addDiscount(veggie0To100Discount);
        discountService.addDiscount(twoForOneDiscount);
        discountService.addDiscount(veggieOver500Discount);
        discountService.addDiscount(threeForOneDiscount);
        discountService.addDiscount(veggie100To500Discount);
        discountService.addDiscount(belgGermanBeerPackDiscount);
        discountService.addDiscount(dutchBeerDiscount);

        var orderService = new OrderService(receiptService, discountService);

        //Order 1: beer - Dutch(8) + Belgian(6) + Belgian6Pack(1), bread - 1day old bread(1), 3day old bread(1), vegetables - 21g onion + 37g carrot
        var order1 = new Order();
        order1.addProduct(dutchBeer, 8);
        order1.addProduct(belgBeer, 6);
        order1.addProduct(belgBeerPack, 1);
        order1.addProduct(sunflowerBread, 2);
        order1.addProduct(whiteBread, 1);
        order1.addProduct(onion, 21);
        order1.addProduct(carrot, 37);

        orderService.addOrder(order1);
        var receiptOfOrder1 = orderService.processOrder(1);
        receiptOfOrder1.print();

        //Order 2: test beer - Dutch(4), bread - 1 White + 1 full Grain, vegetables - 200g broccoli + 44g onion
        var order2 = new Order();
        order2.addProduct(dutchBeer, 4);
        order2.addProduct(whiteBread, 1);
        order2.addProduct(fullGrainBread, 1);
        order2.addProduct(broccoli, 200);
        order2.addProduct(onion, 44);

        orderService.addOrder(order2);
        var receiptOfOrder2 = orderService.processOrder(2);
        receiptOfOrder2.print();

        //Order3: test beer beer - German(9), bread - 3 GlutenFree, vegetables - 400g broccoli + 200g carrot
        var order3 = new Order();
        order3.addProduct(germanBeer, 9);
        order3.addProduct(glutenFreeBread, 3);
        order3.addProduct(broccoli, 400);
        order3.addProduct(carrot, 200);

        orderService.addOrder(order3);
        var receiptOfOrder3 = orderService.processOrder(3);
        receiptOfOrder3.print();

    }
}