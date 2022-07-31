import common.models.discounts.FixedPriceDiscount;
import common.models.discounts.PercentageDiscount;
import common.models.discounts.XForYDiscount;
import common.models.products.beverages.Beer;
import common.models.products.beverages.BeveragePack;
import common.models.products.food.Bread;
import common.models.products.food.Vegetable;
import common.models.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import proccessing.DiscountService;
import proccessing.OrderService;
import proccessing.ReceiptService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
        orderService.addOrder(testOrder);

        var receipt = orderService.processOrder(testOrder.getId());

        assertAll("receipt",
                () -> assertEquals(2,receipt.getRecords().stream().count()),
                () -> assertEquals(new BigDecimal(7).setScale(2, RoundingMode.DOWN),receipt.getTotalSum()),

                () -> assertEquals(String.valueOf(BigDecimal.valueOf(-3.00).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(0).getDiscount()),
                () -> assertEquals(belgBeer.getName(), receipt.getRecords().get(0).getName()),
                () -> assertEquals(belgBeerDiscount.getName(), receipt.getRecords().get(0).getDiscountName()),
                () -> assertEquals(String.valueOf(BigDecimal.valueOf(7.00).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(0).getFullPrice()),
                () -> assertEquals(String.valueOf(1), receipt.getRecords().get(0).getUnitPrice()),
                () -> assertEquals(String.valueOf(7), receipt.getRecords().get(0).getQty()),

                () -> assertEquals(String.valueOf(BigDecimal.valueOf(-9.00).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(1).getDiscount()),
                () -> assertEquals(belgBeerPack.getName(), receipt.getRecords().get(1).getName()),
                () -> assertEquals(belgBeerPackDiscount.getName(), receipt.getRecords().get(1).getDiscountName()),
                () -> assertEquals(String.valueOf(BigDecimal.valueOf(12).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(1).getFullPrice()),
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
        orderService.addOrder(testOrder);

        var receipt = orderService.processOrder(testOrder.getId());

        assertAll("receipt",
                () -> assertEquals(1,receipt.getRecords().stream().count()),
                () -> assertEquals(new BigDecimal(21.6).setScale(2, RoundingMode.DOWN),receipt.getTotalSum()),

                () -> assertEquals(String.valueOf(BigDecimal.valueOf(-2.40).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(0).getDiscount()),
                () -> assertEquals(carrot.getName(), receipt.getRecords().get(0).getName()),
                () -> assertEquals(veggie100To500Discount.getName(), receipt.getRecords().get(0).getDiscountName()),
                () -> assertEquals(String.valueOf(BigDecimal.valueOf(24.00).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(0).getFullPrice()),
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
        orderService.addOrder(testOrder);

        var receipt = orderService.processOrder(testOrder.getId());

        assertAll("receipt",
                () -> assertEquals(1,receipt.getRecords().stream().count()),
                () -> assertEquals(new BigDecimal(6).setScale(2, RoundingMode.DOWN),receipt.getTotalSum()),

                () -> assertEquals(String.valueOf(BigDecimal.valueOf(-3.00).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(0).getDiscount()),
                () -> assertEquals(whiteBread.getName(), receipt.getRecords().get(0).getName()),
                () -> assertEquals(twoForOneDiscount.getName(), receipt.getRecords().get(0).getDiscountName()),
                () -> assertEquals(String.valueOf(BigDecimal.valueOf(9.00).setScale(2, RoundingMode.DOWN)), receipt.getRecords().get(0).getFullPrice()),
                () -> assertEquals(String.valueOf(3), receipt.getRecords().get(0).getUnitPrice()),
                () -> assertEquals(String.valueOf(3), receipt.getRecords().get(0).getQty())
        );
    }

    @Test
    void changeProductQty_withFixedPriceDiscountNotAppliedBefore_expectCreatedReceiptWithFixedPriceDiscount(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");

        testOrder.addProduct(belgBeer, 5);
        discountService.addDiscount(belgBeerDiscount);
        orderService = new OrderService(receiptService, discountService);
        orderService.addOrder(testOrder);

        var order = orderService.changeProductQty(testOrder.getId(), belgBeer.getId(), 6).getOrderProducts().get(0);

        assertAll("receipt",
                () -> assertEquals(new BigDecimal(6).setScale(2, RoundingMode.DOWN),order.getPrice()),
                () -> assertEquals(BigDecimal.valueOf(-3.00).setScale(2, RoundingMode.DOWN), order.getDiscount().getDiscountValue()),
                () -> assertEquals(belgBeerDiscount.getName(), order.getDiscount().getName()),
                () -> assertEquals(6, order.getQty())
        );
    }
    //todo: cant create second receipt based on order
}
