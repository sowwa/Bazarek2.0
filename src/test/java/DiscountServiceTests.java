import common.models.discounts.FixedPriceDiscount;
import common.models.discounts.PercentageDiscount;
import common.models.discounts.XForYDiscount;
import common.models.order.Order;
import common.models.products.beverages.Beer;
import common.models.products.food.Bread;
import common.models.products.food.Vegetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import proccessing.DiscountService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountServiceTests {
    DiscountService discountService;
    Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        discountService = new DiscountService();
    }

    @Test
    void applyDiscounts_fixedPriceDiscount_orderProducstWithFixedDiscount(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        testOrder.addProduct(belgBeer, 6);
        discountService.addDiscount(belgBeerDiscount);
        discountService.applyDiscounts(testOrder.getOrderProducts());

        assertAll("orderProduct",
                () -> assertEquals(new BigDecimal(-3).setScale(2, RoundingMode.DOWN), testOrder.getProduct(belgBeer.getId()).getDiscount().getDiscountValue()),
                () -> assertEquals("Belgian Beer 6 for 3", testOrder.getProduct(belgBeer.getId()).getDiscount().getName())
        );
    }

    @Test
    void applyDiscounts_percentageDiscount_orderProducstWithFixedDiscount(){
        var carrot = new Vegetable("Carrot", new BigDecimal(0.1), LocalDate.now());
        var veggie100To500Discount = new PercentageDiscount(new ArrayList<>(){{add(carrot.getId());}},
                100 , 500, 10, "Vegetables 100-500");
        testOrder.addProduct(carrot, 200);
        discountService.addDiscount(veggie100To500Discount);
        discountService.applyDiscounts(testOrder.getOrderProducts());

        assertAll("orderProduct",
                () -> assertEquals(new BigDecimal(-2).setScale(2, RoundingMode.DOWN), testOrder.getProduct(carrot.getId()).getDiscount().getDiscountValue()),
                () -> assertEquals("Vegetables 100-500", testOrder.getProduct(carrot.getId()).getDiscount().getName())
        );
    }

    @Test
    void applyDiscounts_xForYDiscount_orderProducstWithFixedDiscount(){
        var whiteBread = new Bread("White", new BigDecimal(4), LocalDate.now().minusDays(3));
        var twoForOneDiscount = new XForYDiscount(new ArrayList<>(){{add(whiteBread.getId());}},
                2, 1, 3, "2for1");
        testOrder.addProduct(whiteBread, 3);
        discountService.addDiscount(twoForOneDiscount);
        discountService.applyDiscounts(testOrder.getOrderProducts());

        assertAll("orderProduct",
                () -> assertEquals(new BigDecimal(-4).setScale(2, RoundingMode.DOWN), testOrder.getProduct(whiteBread.getId()).getDiscount().getDiscountValue()),
                () -> assertEquals("2for1", testOrder.getProduct(whiteBread.getId()).getDiscount().getName())
        );
    }

    @Test
    void removeDiscounts_oneDiscountApplied_orderProducstWithoutDiscount(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());
        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        testOrder.addProduct(belgBeer, 6);
        discountService.addDiscount(belgBeerDiscount);
        discountService.applyDiscounts(testOrder.getOrderProducts());
        discountService.removeDiscounts(testOrder.getOrderProducts());

        assertEquals(null, testOrder.getProduct(belgBeer.getId()).getDiscount());
    }

    @Test
    void addDiscount_newDiscount_returnDiscount(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());

        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        discountService.addDiscount(belgBeerDiscount);

        assertEquals(1, discountService.getDiscounts().stream().count());
    }

    @Test
    void addDiscount_existingDiscount_returnDiscount(){
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());

        var belgBeerDiscount = new FixedPriceDiscount(new ArrayList<>(){{add(belgBeer.getId());}},
                6, new BigDecimal(3), "Belgian Beer 6 for 3");
        discountService.addDiscount(belgBeerDiscount);

        Throwable ex = assertThrows(IllegalArgumentException.class, () -> {discountService.addDiscount(belgBeerDiscount);});

        assertEquals("Discount already on list.", ex.getMessage());
    }
}
