package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Discount {
    protected int id;
    protected String name;
    private List<Integer> productsIds;
    private Unit productUnit; //todo: check if unit really needed
    private static final AtomicInteger count = new AtomicInteger(0);
    public Discount(List<Integer> productsIds, Unit productUnit, String name){
        this.productUnit = productUnit;
        this.productsIds = productsIds;
        this.name = name;
        id = count.incrementAndGet();
    }
    public List<Integer> getProductsIds(){
        return this.productsIds;
    }
    public abstract boolean checkIfApplies(List<OrderProduct> discountedProducts);
    public abstract void calculateDiscountPrice(List<OrderProduct> discountedProducts);
}
