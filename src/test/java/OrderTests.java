import common.models.order.Order;
import common.models.products.food.Bread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTests {
    Order testOrder;
    LocalDate date;
    Bread whiteBread;

    @BeforeEach
    void setUp() {
        date = LocalDate.now().minusDays(3);
        whiteBread = new Bread("White", new BigDecimal(10), date);
        testOrder = new Order();
        testOrder.addProduct(whiteBread, 3);
    }

    @Test
    void getProduct_existingProduct_returnProduct(){

        var actualProduct = testOrder.getProduct(whiteBread.getId());

        assertAll("orderProduct",
                () -> assertEquals("White", actualProduct.getProduct().getName()),
                () -> assertEquals(whiteBread.getId(), actualProduct.getProduct().getId()),
                () -> assertEquals(date, actualProduct.getProduct().getCreationDate()),
                () -> assertEquals(BigDecimal.valueOf(10), actualProduct.getProduct().getPrice()),
                () -> assertEquals(BigDecimal.valueOf(30).setScale(2, RoundingMode.DOWN), actualProduct.getPrice()),
                () -> assertEquals(null, actualProduct.getDiscount()),
                () -> assertEquals(3, actualProduct.getQty())
        );
    }

    @Test
    void getProduct_notExistingProduct_throwEx(){

        Throwable ex = assertThrows(NoSuchElementException.class, () -> {testOrder.getProduct(123);});

        assertEquals("Product not in order.", ex.getMessage());
    }
    @Test
    void addProduct_productNotInOrder_returnOrderWithTwoProducts(){
        var newBread = new Bread("New", new BigDecimal(3), LocalDate.now().minusDays(1));
        testOrder.addProduct(newBread, 2);

        assertAll("order",
                () -> assertEquals(2, testOrder.getOrderProducts().stream().count())
        );
    }

    @Test
    void addProduct_productAlreadyInOrderWithoutPriceChange_returnOrderProductWithUpdatedQty(){
        testOrder.addProduct(whiteBread, 2);

        assertAll("order",
                () -> assertEquals(1, testOrder.getOrderProducts().stream().count()),
                () -> assertEquals(5, testOrder.getOrderProducts().get(0).getQty()),
                () -> assertEquals(BigDecimal.valueOf(50).setScale(2, RoundingMode.DOWN), testOrder.getOrderProducts().get(0).getPrice())
        );
    }

    @Test
    void getProduct_breadOver6DaysOld_throwEx(){
        var oldBread = new Bread("Old", new BigDecimal(1), LocalDate.now().minusDays(7));

        Throwable ex = assertThrows(IllegalArgumentException.class, () -> {testOrder.addProduct(oldBread, 1);});

        assertEquals("Bread is older then 6 days.", ex.getMessage());
    }

    @Test
    void modifyProductQty_invalidQty_throwEx(){
        Throwable ex = assertThrows(IllegalArgumentException.class, () -> {testOrder.modifyProductQty(whiteBread.getId(), -4);});

        assertEquals("Quantity can't be a negative number.", ex.getMessage());
    }
}
