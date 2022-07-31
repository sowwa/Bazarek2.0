package common.models.discounts;

import common.models.order.OrderProduct;
import common.models.order.OrderProductDiscount;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import static java.util.Objects.nonNull;

public class PercentageDiscount extends Discount{
    private Integer minQty;
    private Integer maxQty;
    private float percentageOff;
    public PercentageDiscount(List<Integer> productsIds, Integer minQty, Integer maxQty, float percentageOff, String name) {
        super(productsIds, name);
        this.setMinQty(minQty);
        this.setMaxQty(maxQty);
        this.setPercentageOff(percentageOff);
    }
    public void setMinQty(Integer minQty){
        if(minQty >= 0)
            this.minQty = minQty;
        else throw new IllegalArgumentException("Min quantity cannot be negative.");
    }
    public void setMaxQty(Integer maxQty){
        if(nonNull(maxQty) &&  maxQty < 1)
            throw new IllegalArgumentException("Max quantity if set, cannot be less then 0.");
        else
            this.maxQty = maxQty;
    }
    public void setPercentageOff(float percentageOff){
        if(percentageOff > 0)
            this.percentageOff = percentageOff;
        else throw new IllegalArgumentException("Percentage off can't be negative.");
    }
    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        int discountableQty = discountedProducts.stream()
                .map(OrderProduct::getQty)
                .mapToInt(Integer::intValue).sum();

        if(minQty != null && maxQty != null)
            return discountableQty >= minQty && discountableQty < maxQty;

        return minQty == null ? discountableQty < maxQty : discountableQty > minQty;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        var mc = new MathContext(4);
        for (var product : discountedProducts) {
            product.setDiscount(new OrderProductDiscount(this.name,product.getPrice().multiply(BigDecimal.valueOf(percentageOff)
                    .divide(BigDecimal.valueOf(-100), mc)).setScale(2, RoundingMode.DOWN)));
        }
    }
}
