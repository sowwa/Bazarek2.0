package common.models.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    //todo: add cart Id and other classes that may need it
    public int id;
    public List<OrderProduct> orderProducts;
    private static AtomicInteger count = new AtomicInteger(0);
    public Order(){
        orderProducts = new ArrayList<OrderProduct>();
        id = count.incrementAndGet();
    }
    //todo: make some interface for that or move to other class
    public Iterable<OrderProduct> addToCart(OrderProduct product){//todo: make it Product and Qty separate args
        //todo: see if already in cart
        //todo: google try catch and error handling in java
        //todo: check if product is already in backet
        //todo: this way handle beerPack and beer - give them the same id
        this.orderProducts.add(product);
        //todo: check if discounts apply or should be removed

        return this.orderProducts;
    }

    public void checkForDiscounts(OrderProduct orderProduct){
        //todo: check all discounts for product Id or for CartProduct
        if(orderProduct.discount != null && !orderProduct.discount.applied){
            var otherProducts  = orderProducts.stream()
                    .filter(p -> Objects.nonNull(p.discount))
                    .filter(p -> p.discount.discount.id == orderProduct.discount.discount.id).toList(); //current product will be included
            //todo: qtyincart
           // var potentailDiscountedProducts = new ArrayList<CartProduct>(otherProducts);

            orderProduct.discount.discount.calculateDiscountPrice(otherProducts);

        }
        //discountList.stream().filter(d -> d.ProductsIds.contains(b1.Id)).findFirst().orElse(null);
        //todo: check if applied to other products in basket
        //todo: check if condition met
        //todo: apply if needed per CartProduct
        //todo: return CartProducts(s)
    }

    //check if any discounts apply?
}
