package common.models.Discounts;

import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

//todo: think about grams vs unit
public class PercentageDiscount extends Discount{
    public int minQty; //todo: maybe change int to some other type
    public int maxQty;
    public BigDecimal percentageOff; //todo: change to other type
    public PercentageDiscount(List<Integer> productsIds, Unit productUnit) {
        super(productsIds, productUnit);
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
            var discountedPrice = product.price.multiply(percentageOff);
            product.discountedPrice = discountedPrice;
            product.discount.applied = true;
        }
        //todo: return some other type
        return null;
    }

    @Override
    public BigDecimal removeDiscount(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.discountedPrice = null;
            product.discount.applied = false;
        }
        return null;
    }
}
