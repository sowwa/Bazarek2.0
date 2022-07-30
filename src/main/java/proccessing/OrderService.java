package proccessing;

import common.interfaces.IOrderService;
import common.interfaces.IReceiptService;
import common.models.shop.Order;
import common.models.shop.OrderProduct;
import common.models.shop.Receipt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    //@Inject private IReceiptService receiptService;//todo: read about DI in java
    private List<Order> orders; //!!! treat it lika DB
    private IReceiptService receiptService;

    public OrderService(IReceiptService receiptService) {
        orders = new ArrayList<>();
        this.receiptService = receiptService;
    }


    @Override
    public List<Order> Add(Order order) {
        orders.add(order);
        return orders;
    }

    @Override
    public List<Order> Remove(Order order) {
        orders.remove(order);
        return orders;
    }

    @Override
    public List<Order> GetAll() {
        return orders;
    }

    @Override
    public Order Get(int orderId) {
        return orders.stream().filter(o -> o.id == orderId).findFirst().orElse(null);
    }

    @Override
    public List<Order> AddProduct(int orderId, OrderProduct product) {
        var order = Get(orderId);
        order.addToCart(product); // todo: check if list will be updated

        return orders;
    }

    @Override
    public List<Order> RemoveProduct(int orderId, int productId) {
        var order = Get(orderId);
        //todo: implement method
        return null;
    }

    @Override
    public Order CalculateTotalPrice(int orderId) {
        var order = Get(orderId);

        return null;
    }

    @Override
    public Receipt PrintReceipt(int orderId) {
        var receipt = receiptService.Create(Get(orderId));

        return receipt;
    }
}
