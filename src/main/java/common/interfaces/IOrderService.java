package common.interfaces;

import common.models.order.Order;
import common.models.receipt.Receipt;

import java.util.List;

public interface IOrderService {
    Receipt processOrder(int orderId);
    List<Order> getOrders();
    Order getOrder(int orderId);
    Order addOrder(Order order);
    Order changeProductQty(int orderId, int productId, int newQty);
}
