package common.interfaces;

import common.models.order.Order;
import common.models.receipt.Receipt;

public interface IReceiptService {
    Receipt create(Order order);
}
