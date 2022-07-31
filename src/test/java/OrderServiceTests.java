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
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTests {
    ReceiptService receiptService;
    OrderService orderService;
    DiscountService discountService;
    Order testOrder;
    @BeforeEach
    void setUp() {
        testOrder = new Order();
        discountService = new DiscountService();
        receiptService = new ReceiptService();
    }
    @Test
    void processOrder_withFixedPriceDiscountApplied_expectCreatedReceiptWithFixedPriceDiscounts(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeerPack = new BeveragePack<Beer>("Belgium Six Pack", new BigDecimal(12),6, belgBeer, LocalDate.now());

        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        var belgBeerPackDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeerPack.getId());}},
                1, new BigDecimal(3), "Belgian Beer Pack 6 for 3");

        testOrder.addProduct(belgBeer, 7);
        testOrder.addProduct(belgBeerPack, 1);
        discountService.addDiscount(belgBeerDiscount);
        discountService.addDiscount(belgBeerPackDiscount);
        orderService = new OrderService(receiptService, discountService);

        var receipt = orderService.ProcessOrder(testOrder);

        assertAll("receipt",
                () -> assertEquals(2,receipt.getRecords().stream().count()),
                () -> assertEquals(new BigDecimal(7),receipt.getTotalSum()),

                () -> assertEquals(String.valueOf(-3), receipt.getRecords().get(0).getDiscount()),
                () -> assertEquals(belgBeer.getName(), receipt.getRecords().get(0).getName()),
                () -> assertEquals(belgBeerDiscount.getName(), receipt.getRecords().get(0).getDiscountName()),
                () -> assertEquals(String.valueOf(7), receipt.getRecords().get(0).getFullPrice()),
                () -> assertEquals(String.valueOf(1), receipt.getRecords().get(0).getUnitPrice()),
                () -> assertEquals(String.valueOf(7), receipt.getRecords().get(0).getQty()),

                () -> assertEquals(String.valueOf(-9), receipt.getRecords().get(1).getDiscount()),
                () -> assertEquals(belgBeerPack.getName(), receipt.getRecords().get(1).getName()),
                () -> assertEquals(belgBeerPackDiscount.getName(), receipt.getRecords().get(1).getDiscountName()),
                () -> assertEquals(String.valueOf(12), receipt.getRecords().get(1).getFullPrice()),
                () -> assertEquals(String.valueOf(12), receipt.getRecords().get(1).getUnitPrice()),
                () -> assertEquals(String.valueOf(1), receipt.getRecords().get(1).getQty())
        );
    }

    @Test
    void processOrder_withPercentagePriceDiscountApplied_expectCreatedReceiptWithPercentagePriceDiscounts(){
        var mc = new MathContext(3);
        var unitPrice = new BigDecimal(0.2, mc).stripTrailingZeros();
        var carrot = new Vegetable("Carrot", unitPrice, LocalDate.now());
        var veggie100To500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
                 100 , 500, 10, "Vegetables 100-500");


        testOrder.addProduct(carrot, 120);
        discountService.addDiscount(veggie100To500Discount);
        orderService = new OrderService(receiptService, discountService);

        var receipt = orderService.ProcessOrder(testOrder);

        assertAll("receipt",
                () -> assertEquals(1,receipt.getRecords().stream().count()),
                () -> assertEquals(new BigDecimal(21.6).round(mc).stripTrailingZeros(),receipt.getTotalSum()),

                () -> assertEquals(String.valueOf(-2.4), receipt.getRecords().get(0).getDiscount()),
                () -> assertEquals(carrot.getName(), receipt.getRecords().get(0).getName()),
                () -> assertEquals(veggie100To500Discount.getName(), receipt.getRecords().get(0).getDiscountName()),
                () -> assertEquals(String.valueOf(24), receipt.getRecords().get(0).getFullPrice()),
                () -> assertEquals(String.valueOf(unitPrice), receipt.getRecords().get(0).getUnitPrice()),
                () -> assertEquals(String.valueOf(120), receipt.getRecords().get(0).getQty())
        );
    }

    @Test
    void processOrder_withXForYDiscountApplied_expectCreatedReceiptWithXForYDiscounts(){
        var whiteBread = new Bread("White", new BigDecimal(3), LocalDate.now().minusDays(3));
        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());}},
               2, 1, 3, "2for1");
        testOrder.addProduct(whiteBread, 3);
        discountService.addDiscount(twoForOneDiscount);
        orderService = new OrderService(receiptService, discountService);

        var receipt = orderService.ProcessOrder(testOrder);

        assertAll("receipt",
                () -> assertEquals(1,receipt.getRecords().stream().count()),
                () -> assertEquals(new BigDecimal(6),receipt.getTotalSum()),

                () -> assertEquals(String.valueOf(-3), receipt.getRecords().get(0).getDiscount()),
                () -> assertEquals(whiteBread.getName(), receipt.getRecords().get(0).getName()),
                () -> assertEquals(twoForOneDiscount.getName(), receipt.getRecords().get(0).getDiscountName()),
                () -> assertEquals(String.valueOf(9), receipt.getRecords().get(0).getFullPrice()),
                () -> assertEquals(String.valueOf(3), receipt.getRecords().get(0).getUnitPrice()),
                () -> assertEquals(String.valueOf(3), receipt.getRecords().get(0).getQty())
        );
    }

    //todo: cant create second receipt based on order
}
