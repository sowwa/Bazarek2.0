package proccessing;

import common.interfaces.IDiscountService;
import common.interfaces.IOrderService;
import common.interfaces.IReceiptService;
import common.models.order.Order;
import common.models.receipt.Receipt;

import java.math.BigDecimal;
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
        if(orders.stream().noneMatch(o -> o.getId() == orderId))
            throw new IllegalArgumentException("Order don't exists.");

        return orders.stream().filter(o -> o.getId() == orderId).findFirst().get();
    }

    @Override
    public Order addOrder(Order order){
        if(orders.stream().noneMatch(o -> o.getId() == order.getId())){
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
    public Order changeProductPrice(int orderId, int productId, BigDecimal newPrice) {
        if(newPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Product price cannot be negative.");

        var order = getOrder(orderId);
        order.modifyProductPrice(productId, newPrice);

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
