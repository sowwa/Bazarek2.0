package common.models.shop;

import java.math.BigDecimal;

public class ReceiptRecord {
    private String name;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal fullPrice;
    private BigDecimal discount;
    private String discountName;

    public ReceiptRecord(String name, int qty, BigDecimal unitPrice, BigDecimal fullPrice, BigDecimal discount, String discountName) {
        this.name = name;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.fullPrice = fullPrice;
        this.discount = discount;
        this.discountName = discountName;
    }

    public void print(){
        System.out.println("\n" + name + "   "
                + qty + "*" + unitPrice + "   "
                + fullPrice + "\n"
                + "Discount: " + discountName + "   " + discount
                );
    }
}
