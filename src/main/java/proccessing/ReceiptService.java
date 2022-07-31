package proccessing;

import common.interfaces.IReceiptService;
import common.models.shop.Order;
import common.models.shop.Receipt;
import common.models.shop.ReceiptRecord;

import java.math.BigDecimal;

public class ReceiptService implements IReceiptService {

    // todo: list as a storage for receipts?
    @Override
    public Receipt Create(Order order) {
        var receipt = new Receipt();
        var sum = BigDecimal.ZERO;
        for (var orderRecord : order.getOrderProducts()) {
            var record = new ReceiptRecord(orderRecord.getProduct().getName(), orderRecord.getQty(), orderRecord.getProduct().getPrice(),
                    orderRecord.getPrice(), orderRecord.getDiscountValue(), orderRecord.getDiscount() != null ? orderRecord.getDiscount().getName() : null);
            receipt.getRecords().add(record);
            sum = sum.add(orderRecord.getPrice()).add(orderRecord.getDiscountValue());
        }
        receipt.setTotalSum(sum);

        return receipt;
    }
}
