package common.models.Discounts;

import common.models.Product;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.List;

public class FixedPriceDiscount extends Discount{
    public BigDecimal FixedPrice;
    public int RequiredQty;

    public FixedPriceDiscount(List<Integer> ProductsIds, Unit ProductUnit, int RequiredQty) {
        super(ProductsIds, ProductUnit);
        this.RequiredQty = RequiredQty;
    }

    //todo: in this case method param not necessary - think about it
    @Override
    public BigDecimal CalculateDiscountPrice(Iterable<Product> discountedProducts) {
        return FixedPrice;
    }
}
