package proccessing;

import common.interfaces.IDiscountService;
import common.interfaces.IOrderService;
import common.interfaces.IReceiptService;
import common.models.order.Order;
import common.models.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    private IReceiptService receiptService;
    private IDiscountService discountService;
    private List<Order> orders;

    public OrderService(IReceiptService receiptService, IDiscountService discountService) {
        this.receiptService = receiptService;
        this.discountService = discountService;
        orders = new ArrayList<>();
    }

    public List<Order> getOrders(){return this.orders;}

    @Override
    public Order getOrder(int orderId) {
        var order = orders.stream().filter(o -> o.getId() == orderId).findFirst().get();

        if(order == null)
            throw new IllegalArgumentException("Order don't exists.");

        return order;
    }

    public Order addOrder(Order order){
        if(!orders.stream().anyMatch(o -> o.getId() == order.getId())){
            orders.add(order);
            return order;
        }
        throw new IllegalArgumentException("Order already on list.");
    }

    @Override
    public Order changeProductQty(int orderId, int productId, int newQty) {
        if(newQty < 0)
            throw new IllegalArgumentException("Product quantity cannot be negative.");

        var order = getOrder(orderId);
        order.modifyProductQty(productId, newQty);

        var orderProducts = order.getOrderProducts();
        discountService.removeDiscounts(orderProducts);
        discountService.applyDiscounts(orderProducts);

        return order;
    }

    @Override
    public Receipt processOrder(int orderId) {
        var order = getOrder(orderId);

        discountService.applyDiscounts(order.getOrderProducts());

        return receiptService.create(order);
    }
}
