package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;
import common.models.shop.OrderProductDiscount;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class PercentageDiscount extends Discount{
    private Integer minQty; //todo: let change values??
    private Integer maxQty;
    private float percentageOff;
    public PercentageDiscount(List<Integer> productsIds, Unit productUnit, Integer minQty, Integer maxQty, float percentageOff, String name) {
        super(productsIds, productUnit, name);
        this.minQty = minQty;
        this.maxQty = maxQty;
        this.percentageOff = percentageOff;
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
            product.setDiscountValue(product.getPrice().multiply(BigDecimal.valueOf(percentageOff)
                    .divide(BigDecimal.valueOf(-100), mc),mc));
            product.setDiscount(new OrderProductDiscount(this.name,true));
        }
    }
}
