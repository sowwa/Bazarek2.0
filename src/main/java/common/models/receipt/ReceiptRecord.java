package common.models.receipt;

import java.math.BigDecimal;

public class ReceiptRecord {
    private final String name;
    private final String qty;
    private final String unitPrice;
    private final String fullPrice;
    private final String discount;
    private final String discountName;

    public ReceiptRecord(String name, int qty, BigDecimal unitPrice, BigDecimal fullPrice, BigDecimal discount, String discountName) {
        this.name = name;
        this.qty = String.valueOf(qty);
        this.unitPrice = unitPrice.toPlainString();
        this.fullPrice = fullPrice.toPlainString();
        this.discount = discount != null ? discount.toPlainString() : "";
        this.discountName = discountName;
    }
    public String getName(){return this.name;}
    public String getQty(){return this.qty;}
    public String getUnitPrice(){return this.unitPrice;}
    public String getFullPrice(){return this.fullPrice;}
    public String getDiscount(){return this.discount;}
    public String getDiscountName(){return this.discountName;}

    public void print(){
        System.out.println("\n" + name + "   "
                + qty + "*" + unitPrice + "           "
                + fullPrice);

        if(!discount.equals(""))
            System.out.println("Discount: " + discountName + "   " + discount);
    }
}
