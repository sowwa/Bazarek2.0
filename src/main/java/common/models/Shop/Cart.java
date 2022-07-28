package common.models.Shop;

import common.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public List<CartProduct> CartProducts;
    public Cart(){
        CartProducts = new ArrayList<CartProduct>();
    }
    //todo: make some interface for that or move to other class
    public Iterable<CartProduct> AddToCart(CartProduct product){//todo: make it Product and Qty separate args
        //todo: see if already in cart
        //todo: google try catch and error handling in java
        //todo: check if product is already in backet
        //todo: this way handle beerPack and beer - give them the same id
        this.CartProducts.add(product);
        //todo: check if discounts apply or should be removed

        return this.CartProducts;
    }

    public void CheckForDiscounts(CartProduct cartProduct){
        //todo: check all discounts for product Id or for CartProduct
        if(cartProduct.Discount != null && !cartProduct.Discount.Applied){
            var otherProducts  = CartProducts.stream()
                    .filter(p -> p.Discount.Discount.Id == cartProduct.Discount.Discount.Id).toList(); //current product will be included
            //todo: qtyincart
           // var potentailDiscountedProducts = new ArrayList<CartProduct>(otherProducts);

            cartProduct.Discount.Discount.CalculateDiscountPrice(otherProducts);

        }
        //discountList.stream().filter(d -> d.ProductsIds.contains(b1.Id)).findFirst().orElse(null);
        //todo: check if applied to other products in basket
        //todo: check if condition met
        //todo: apply if needed per CartProduct
        //todo: return CartProducts(s)
    }

    //check if any discounts apply?
}
