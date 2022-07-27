package common.models.Discounts;

import common.models.Product;

import java.math.BigDecimal;

public class XForYDiscount extends Discount {
    public int X;
    public int Y;

    @Override
    public BigDecimal CalculateDiscountPrice(Iterable<Product> discountedProducts) {
        //todo: lowest for free
        return null;
    }
}
