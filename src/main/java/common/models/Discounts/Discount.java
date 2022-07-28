package common.models.Discounts;

import common.models.Product;
import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Discount {
    public int Id;
    public List<Integer> ProductsIds;
    public Unit ProductUnit;
    //todo: add methods: Calculate price, check discount(maybe in other place, cart?)

    private static AtomicInteger count = new AtomicInteger(0);
    public Discount(List<Integer> ProductsIds, Unit ProductUnit){
        this.ProductUnit = ProductUnit;
        this.ProductsIds = ProductsIds;
        Id = count.incrementAndGet();
    }
    //todo: add to discounted Products and remove
    public abstract BigDecimal CalculateDiscountPrice(List<CartProduct> discountedProducts);
    public abstract BigDecimal RemoveDiscount(List<CartProduct> discountedProducts);
}
