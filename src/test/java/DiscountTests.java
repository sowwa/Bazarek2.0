import common.models.discounts.FixedPriceDiscount;
import common.models.discounts.PercentageDiscount;
import common.models.discounts.XForYDiscount;
import common.models.order.Order;
import common.models.products.beverages.Beer;
import common.models.products.food.Bread;
import common.models.products.food.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountTests {
    Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
    }

    @Test
    void checkIfApplies_fixedPriceDiscount_returnTrue(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        testOrder.addProduct(belgBeer, 6);

        var actualValue = belgBeerDiscount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, true);
    }
    @Test
    void checkIfApplies_fixedPriceDiscount_returnFalse(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        testOrder.addProduct(belgBeer, 2);
        var actualValue = belgBeerDiscount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, false);
    }
    @Test
    void checkIfApplies_percentageDiscount_returnTrue(){
        var carrot = new Vegetable("Carrot", new BigDecimal(0.1), LocalDate.now());
        var veggie100To500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
                100 , 500, 7, "Vegetables 100-500");
        testOrder.addProduct(carrot, 200);
        var actualValue = veggie100To500Discount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, true);
    }
    @Test
    void checkIfApplies_percentageDiscountQtyLessThenMin_returnFalse(){
        var carrot = new Vegetable("Carrot", new BigDecimal(0.67), LocalDate.now());
        var veggie100To500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
                100 , 500, 7, "Vegetables 100-500");
        testOrder.addProduct(carrot, 34);
        var actualValue = veggie100To500Discount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, false);
    }

    @Test
    void checkIfApplies_percentageDiscountQtyMoreThenMax_returnFalse(){
        var carrot = new Vegetable("Carrot", new BigDecimal(0.67), LocalDate.now());
        var veggie100To500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
                100 , 500, 7, "Vegetables 100-500");
        testOrder.addProduct(carrot, 515);
        var actualValue = veggie100To500Discount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, false);
    }

    @Test
    void checkIfApplies_xForYDiscount_returnTrue(){
        var whiteBread = new Bread("White", new BigDecimal(1.2), LocalDate.now().minusDays(3));
        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());}},
                2, 1, 3, "2for1");
        testOrder.addProduct(whiteBread, 2);
        var actualValue = twoForOneDiscount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, true);
    }
    @Test
    void checkIfApplies_xForYDiscountNOtEnoughtQty_returnFalse(){
        var whiteBread = new Bread("White", new BigDecimal(1.2), LocalDate.now().minusDays(3));
        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());}},
                2, 1, 3, "2for1");
        testOrder.addProduct(whiteBread, 1);
        var actualValue = twoForOneDiscount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, false);
    }
    @Test
    void checkIfApplies_xForYDiscountNotOldEnought_returnFalse(){
        var whiteBread = new Bread("White", new BigDecimal(1.2), LocalDate.now().minusDays(1));
        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());}},
                2, 1, 3, "2for1");
        testOrder.addProduct(whiteBread, 2);
        var actualValue = twoForOneDiscount.checkIfApplies(testOrder.getOrderProducts());

        assertEquals(actualValue, false);
    }
}
