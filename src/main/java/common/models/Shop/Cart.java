package common.models.Shop;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public List<CartProduct> CartProducts;
    public Cart(){
        CartProducts = new ArrayList<CartProduct>();
    }
    //todo: make some interface for that or move to other class
    public Iterable<CartProduct> AddToCart(CartProduct product){
        //todo: google try catch and error handling in java
        //todo: check if product is already in backet
        //todo: this way handle beerPack and beer - give them the same id
        this.CartProducts.add(product);
        //todo: check if discounts apply or should be removed

        return this.CartProducts;
    }
}
