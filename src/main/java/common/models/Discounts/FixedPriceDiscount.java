package common.models.Discounts;

import common.models.Product;

import java.math.BigDecimal;

public class FixedPriceDiscount extends Discount{
    public BigDecimal FixedPrice;
    public int RequiredQty;
    @Override
    public BigDecimal CalculateDiscountPrice(Iterable<Product> discountedProducts) {
        return FixedPrice;
    }
}
