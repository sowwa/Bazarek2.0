package common.models.Discounts;

import common.models.Product;
import common.models.enums.Unit;

import java.math.BigDecimal;

public abstract class Discount {
    public Iterable<Product> Products;
    public Unit ProductUnit;
    //todo: add methods: Calculate price, check discount(maybe in other place, cart?)
    public abstract BigDecimal CalculateDiscountPrice(Iterable<Product> discountedProducts);
}
