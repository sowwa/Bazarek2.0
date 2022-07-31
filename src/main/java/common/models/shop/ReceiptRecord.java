package common.models.shop;

import java.math.BigDecimal;

public class ReceiptRecord {
    private String name;
    private String qty;
    private String unitPrice;
    private String fullPrice;
    private String discount;
    private String discountName;

    public ReceiptRecord(String name, int qty, BigDecimal unitPrice, BigDecimal fullPrice, BigDecimal discount, String discountName) {
        this.name = name;
        this.qty = String.valueOf(qty);
        this.unitPrice = unitPrice.stripTrailingZeros().toPlainString();
        this.fullPrice = fullPrice.stripTrailingZeros().toPlainString();
        this.discount = discount.stripTrailingZeros().toPlainString();
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
                + qty + "*" + unitPrice + "   "
                + fullPrice);

        if(!discount.equals("0"))
            System.out.println("Discount: " + discountName + "   " + discount);
    }
}
