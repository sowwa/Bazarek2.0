package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Discount {
    public int id;
    public List<Integer> productsIds;
    //todo: check if unit really needed
    public Unit productUnit;
    private static AtomicInteger count = new AtomicInteger(0);
    public Discount(List<Integer> productsIds, Unit productUnit){
        this.productUnit = productUnit;
        this.productsIds = productsIds;
        id = count.incrementAndGet();
    }
    //todo: add to discounted Products and remove
    public abstract boolean checkIfApplies(List<OrderProduct> discountedProducts);
    public abstract void calculateDiscountPrice(List<OrderProduct> discountedProducts);
}
