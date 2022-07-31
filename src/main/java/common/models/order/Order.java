package common.models.order;

import common.models.products.Product;
import common.models.products.food.Bread;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private int id;
    private List<OrderProduct> orderProducts;
    private static final AtomicInteger count = new AtomicInteger(0);
    public Order(){
        orderProducts = new ArrayList<>();
        id = count.incrementAndGet();
    }

    public int getId(){return this.id;}
    public List<OrderProduct> getOrderProducts(){return this.orderProducts;}
    public OrderProduct getProduct(int productId){
        if(this.orderProducts.stream().anyMatch(p -> p.getId() == productId))
            return this.orderProducts.stream().filter(p -> p.getId() == productId).findFirst().get();
        throw new NoSuchElementException("Product not in order.") ;
    }
    public void addProduct(Product product, int qty){
        //todo: make sure not to add bread older then 6 days
        if(orderProducts.stream().anyMatch(o -> o.getProduct().getId() == product.getId())){
            orderProducts.stream().filter(o -> o.getProduct().getId() == product.getId())
                    .forEach(o -> setUpdatedValues(o, product, qty));
        }
        else{
            if(product.getClass() == Bread.class){
                if(product.getAgeInDays() > 6)
                    throw new IllegalArgumentException("Bread is older then 6 days.");
            }
            this.orderProducts.add(new OrderProduct(product, qty, null));
        }
    }
    public void modifyProductQty(int productId, int newQty){
        var product = getProduct(productId);
        product.setQty(newQty);
    }
    private void setUpdatedValues(OrderProduct currentOrderProduct, Product newProduct, int newProductQty){
        currentOrderProduct.setPrice(newProduct.getPrice());
        currentOrderProduct.setQty(currentOrderProduct.getQty() + newProductQty);
        currentOrderProduct.calculatePrice();
    }
}
