package common.models.discounts;

import common.models.shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.Collectors;

//todo: think about grams vs unit
public class PercentageDiscount extends Discount{
    public int minQty; //todo: maybe change int to some other type
    public int maxQty;
    public float percentageOff; //todo: change to other type
    public PercentageDiscount(List<Integer> productsIds, Unit productUnit, int minQty, int maxQty, float percentageOff) {
        super(productsIds, productUnit);
        this.minQty = minQty;
        this.maxQty = maxQty;
        this.percentageOff = percentageOff;
    }

    @Override
    public boolean checkIfApplies(List<CartProduct> discountedProducts){
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        //todo: what if min or max are null?
        return qty >= minQty || qty < maxQty;
    }

    @Override
    public BigDecimal calculateDiscountPrice(List<CartProduct> discountedProducts) {
        for (var product : discountedProducts) {
            var discount = product.price.multiply(BigDecimal.valueOf(percentageOff/-100),new MathContext(2));
            product.discountValue = discount;
            product.discount.applied = true;
        }
        //todo: return some other type
        return null;
    }

    @Override
    public BigDecimal removeDiscount(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.discountValue = null;
            product.discount.applied = false;
        }
        return null;
    }
}
