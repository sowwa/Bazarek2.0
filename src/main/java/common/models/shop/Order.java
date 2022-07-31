package common.models.shop;

import common.models.products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private int id;
    private List<OrderProduct> orderProducts;
    private static final AtomicInteger count = new AtomicInteger(0);
    public Order(){
        orderProducts = new ArrayList<>();
        id = count.incrementAndGet();
    }

    public List<OrderProduct> getOrderProducts(){return this.orderProducts;}
    public void addProduct(Product product, int qty){
        if(orderProducts.stream().anyMatch(o -> o.getProduct().getId() == product.getId())){
            orderProducts.stream().filter(o -> o.getProduct().getId() == product.getId())
                    .forEach(o -> setUpdatedValues(o,qty));
        }
        else{
            this.orderProducts.add(new OrderProduct(product, qty, null));
        }
    }
    private void setUpdatedValues(OrderProduct currentOrderProduct, int newProductQty){
        currentOrderProduct.setQty(currentOrderProduct.getQty() + newProductQty);
        currentOrderProduct.calculatePrice();
    }
}
