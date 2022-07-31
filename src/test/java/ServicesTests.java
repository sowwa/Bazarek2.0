import common.models.discounts.FixedPriceDiscount;
import common.models.discounts.PercentageDiscount;
import common.models.discounts.XForYDiscount;
import common.models.products.beverages.Beer;
import common.models.products.beverages.BeveragePack;
import common.models.products.food.Bread;
import common.models.products.food.Vegetable;
import common.models.shop.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import proccessing.DiscountService;
import proccessing.OrderService;
import proccessing.ReceiptService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServicesTests {
    ReceiptService receiptService;
    OrderService orderService;
    DiscountService discountService;
    Order testOrder;
    @BeforeEach
    void setUp() {
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeerPack = new BeveragePack<Beer>("Belgium Six Pack", new BigDecimal(12),6, belgBeer, LocalDate.now());
        var whiteBread = new Bread("White", new BigDecimal(1.2), LocalDate.now().minusDays(3));
        var carrot = new Vegetable("Carrot", new BigDecimal(0.67), LocalDate.now());
        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());}},
                 2, 1, 3, "2for1");
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        var belgBeerPackDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeerPack.getId());}},
                1, new BigDecimal(3), "Belgian Beer 6 for 3");
        var veggie0To100Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
                0 , 100, 5, "Vegetables 0-100");
        var veggie100To500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
               100 , 500, 7, "Vegetables 100-500");
        var veggieOver500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
                500 , null, 10, "Vegetables over 500");

        testOrder = new Order();
        testOrder.addProduct(belgBeer, 7);
        testOrder.addProduct(belgBeerPack, 1);
        testOrder.addProduct(whiteBread, 3);
        testOrder.addProduct(carrot, 100);

        discountService = new DiscountService();

        discountService.addDiscount(belgBeerDiscount);
        discountService.addDiscount(belgBeerPackDiscount);
        discountService.addDiscount(veggie100To500Discount);
        discountService.addDiscount(veggie0To100Discount);
        discountService.addDiscount(twoForOneDiscount);
        discountService.addDiscount(veggieOver500Discount);
        receiptService = new ReceiptService();

        orderService = new OrderService(receiptService, discountService);
    }
    @Test
    void create_withoutDiscountsApplied_expectCreatedReceiptWithoutDiscounts(){
        var receipt = receiptService.Create(testOrder);

        assertAll("receipt",
                () -> assertEquals(4,receipt.getRecords().stream().count())

        );
    }

    @Test
    void testAddToCart() {


    }

    @Test
    void checkForDiscounts() {

    }
    //todo: cant create second receipt based on order
}
