package common.models.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderProductDiscount {
    private String discountName;
    private BigDecimal discountValue;

    public OrderProductDiscount(String discountName, BigDecimal discountValue){
        this.discountName = discountName;
        this.setDiscountValue(discountValue);
    }
    public String getName(){return discountName;}
    public BigDecimal getDiscountValue(){return this.discountValue;}
    public void setDiscountValue(BigDecimal discountValue){this.discountValue = discountValue.setScale(2, RoundingMode.DOWN);}
}
