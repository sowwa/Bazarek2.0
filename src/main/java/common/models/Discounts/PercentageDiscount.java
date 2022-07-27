package common.models.Discounts;

import common.models.Product;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.List;

//todo: think about grams vs unit
public class PercentageDiscount extends Discount{
    public int MinQty; //todo: maybe change int to some other type
    public int MaxQty;

    public PercentageDiscount(List<Integer> ProductsIds, Unit ProductUnit) {
        super(ProductsIds, ProductUnit);
    }

    @Override
    public BigDecimal CalculateDiscountPrice(Iterable<Product> discountedProducts) {
        return null;
    }
}
