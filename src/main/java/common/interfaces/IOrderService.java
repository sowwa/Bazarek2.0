package common.interfaces;

import common.models.shop.Order;
import common.models.shop.Receipt;

public interface IOrderService {
    Receipt ProcessOrder(Order order);
}
