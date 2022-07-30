package common.interfaces;

import common.models.shop.Order;
import common.models.shop.OrderProduct;
import common.models.shop.Receipt;

import java.util.List;

public interface IOrderService {
    List<Order> Add(Order order);
    List<Order> Remove(Order order);
    List<Order> GetAll();
    Order Get(int orderId);
    List<Order> AddProduct(int orderId,OrderProduct product);
    List<Order> RemoveProduct(int orderId, int productId);
    Order CalculateTotalPrice(int orderId); //todo: think if neccesary
    Receipt PrintReceipt(int orderId); //todo: rename maybe to finish order: FINISHORDER?
}
