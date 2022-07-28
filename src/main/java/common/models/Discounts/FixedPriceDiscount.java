package common.models.Discounts;

import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FixedPriceDiscount extends Discount{
    public BigDecimal fixedPrice;
    public int requiredQty;

    public FixedPriceDiscount(List<Integer> productsIds, Unit productUnit, int requiredQty) {
        super(productsIds, productUnit);
        this.requiredQty = requiredQty;
    }

    @Override
    public boolean checkIfApplies(List<CartProduct> discountedProducts){
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));

        return qty % requiredQty == 0;
    }

    @Override
    public BigDecimal calculateDiscountPrice(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.discountedPrice = fixedPrice;
            product.discount.applied = true;
        }
        return fixedPrice;
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
