package common.interfaces;

import common.models.shop.Order;
import common.models.shop.Receipt;

public interface IReceiptService {
    Receipt Create(Order order);
    Receipt Get(int receiptid);
    Receipt Remove(int receiptId);
}
