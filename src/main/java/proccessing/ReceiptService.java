package proccessing;

import common.interfaces.IReceiptService;
import common.models.order.Order;
import common.models.order.OrderProduct;
import common.models.order.OrderProductDiscount;
import common.models.receipt.Receipt;
import common.models.receipt.ReceiptRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReceiptService implements IReceiptService {

    private List<Receipt> receipts;

    public ReceiptService(){
        receipts = new ArrayList<>();
    }

    public List<Receipt> getReceipts(){return this.receipts;}

    @Override
    public Receipt create(Order order) {
        if(receipts.stream().anyMatch(r -> r.getId() == order.getId()))
            throw new IllegalArgumentException("Receipt already on list");

        var receipt = new Receipt(order.getId());
        var orderTotalSum = BigDecimal.ZERO;

        for (var orderRecord : order.getOrderProducts()) {
            var discount = orderRecord.getDiscount();

            var record = createRecord(orderRecord, discount);
            receipt.addRecord(record);

            orderTotalSum = orderTotalSum.add(orderRecord.getPrice());
            if(discount != null)
                orderTotalSum = orderTotalSum.add(orderRecord.getDiscount().getDiscountValue());
        }

        receipt.setTotalSum(orderTotalSum);
        addReceipt(receipt);
        return receipt;
    }

    private ReceiptRecord createRecord(OrderProduct orderRecord, OrderProductDiscount discount){
        var discountValue = discount != null ? discount.getDiscountValue() : null;
        var discountName = discount != null ? discount.getName() : null;

        return new ReceiptRecord(orderRecord.getProduct().getName(), orderRecord.getQty(), orderRecord.getProduct().getPrice(),
                orderRecord.getPrice(), discountValue, discountName);
    }
    private void addReceipt(Receipt receipt){
        if(receipts.stream().noneMatch(r -> r.getId() == receipt.getId()))
            receipts.add(receipt);
        else throw new IllegalArgumentException("Receipt already on list");
    }
}
