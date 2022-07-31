package common.models.discounts;

import common.models.order.OrderProduct;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Discount {
    protected int id;
    protected String name;
    private List<Integer> productsIds;
    private static final AtomicInteger count = new AtomicInteger(0);
    public Discount(List<Integer> productsIds,  String name){
        this.productsIds = productsIds;
        this.name = name;
        id = count.incrementAndGet();
    }
    public String getName(){return this.name;}
    public int getId(){return this.id;}
    public List<Integer> getProductsIds(){return this.productsIds;}
    public abstract boolean checkIfApplies(List<OrderProduct> discountedProducts);
    public abstract void calculateDiscountPrice(List<OrderProduct> discountedProducts);
}
