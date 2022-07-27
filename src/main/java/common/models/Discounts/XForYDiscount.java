package common.models.Discounts;

import common.models.Product;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.List;

public class XForYDiscount extends Discount {
    public int X;
    public int Y;

    public XForYDiscount(List<Integer> ProductsIds, Unit ProductUnit) {
        super(ProductsIds, ProductUnit);
    }

    @Override
    public BigDecimal CalculateDiscountPrice(Iterable<Product> discountedProducts) {
        //todo: lowest for free
        return null;
    }
}
