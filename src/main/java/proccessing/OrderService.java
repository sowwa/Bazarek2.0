package proccessing;

import common.interfaces.IDiscountService;
import common.interfaces.IOrderService;
import common.interfaces.IReceiptService;
import common.models.shop.Order;
import common.models.shop.Receipt;

public class OrderService implements IOrderService {
    private IReceiptService receiptService;
    private IDiscountService discountService;

    public OrderService(IReceiptService receiptService, IDiscountService discountService) {
        this.receiptService = receiptService;
        this.discountService = discountService;
    }

    @Override
    public Receipt ProcessOrder(Order order) {
        discountService.ApplyDiscounts(order.getOrderProducts());
        var receipt = receiptService.Create(order);

        return receipt;
    }
}
