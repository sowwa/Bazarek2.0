package common.models.discounts;

import common.models.shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Discount {
    public int id;
    public List<Integer> productsIds;
    //todo: check if unit really needed
    public Unit productUnit;
    //todo: add methods: Calculate price, check discount(maybe in other place, cart?)
    private static AtomicInteger count = new AtomicInteger(0);
    public Discount(List<Integer> productsIds, Unit productUnit){
        this.productUnit = productUnit;
        this.productsIds = productsIds;
        id = count.incrementAndGet();
    }
    //todo: add to discounted Products and remove
    public abstract boolean checkIfApplies(List<CartProduct> discountedProducts);
    public abstract BigDecimal calculateDiscountPrice(List<CartProduct> discountedProducts);
    public abstract BigDecimal removeDiscount(List<CartProduct> discountedProducts);
}
