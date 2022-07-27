package common.models.Discounts;

import common.models.Product;

import java.math.BigDecimal;
//todo: think about grams vs unit
public class PercentageDiscount extends Discount{
    public int MinQty; //todo: maybe change int to some other type
    public int MaxQty;
    @Override
    public BigDecimal CalculateDiscountPrice(Iterable<Product> discountedProducts) {
        return null;
    }
}
