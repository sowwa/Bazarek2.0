import common.models.order.Order;
import common.models.order.OrderProductDiscount;
import common.models.products.beverages.Beer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import proccessing.ReceiptService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptServiceTests {
    ReceiptService receiptService;
    Order testOrder;

    @BeforeEach
    void setUp() {
        var belgBeer = new Beer("Belgium", new BigDecimal(1), LocalDate.now());

        testOrder = new Order();
        receiptService = new ReceiptService();
        testOrder.addProduct(belgBeer, 6);
    }

    @Test
    void create_newReceiptWithDiscount_returnReceiptWithDiscount(){
        testOrder.getOrderProducts().get(0).setDiscount(new OrderProductDiscount("TestDiscount", new BigDecimal(-3)));

        var actualReceipt = receiptService.create(testOrder);

        assertAll("receipt",
                () -> assertEquals(1, (long) actualReceipt.getRecords().size()),
                () -> assertEquals(BigDecimal.valueOf(3).setScale(2, RoundingMode.DOWN), actualReceipt.getTotalSum())
        );
    }

    @Test
    void create_newReceiptWithoutDiscount_returnReceiptWithoutDiscount(){
        var actualReceipt = receiptService.create(testOrder);

        assertAll("receipt",
                () -> assertEquals(1, (long) actualReceipt.getRecords().size()),
                () -> assertEquals(BigDecimal.valueOf(6).setScale(2, RoundingMode.DOWN), actualReceipt.getTotalSum())
        );
    }

    @Test
    void create_receiptAlreadyExistsForOrder_throwEx(){
        testOrder.getOrderProducts().get(0).setDiscount(new OrderProductDiscount("TestDiscount", new BigDecimal(-3)));
        receiptService.create(testOrder);

        Throwable ex = assertThrows(IllegalArgumentException.class, () -> receiptService.create(testOrder));

        assertEquals("Receipt already on list", ex.getMessage());

    }
}
