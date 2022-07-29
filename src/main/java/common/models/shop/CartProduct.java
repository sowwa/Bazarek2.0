package common.models.shop;

import common.models.Product;

import java.math.BigDecimal;

public class CartProduct {
    public Product product;
    public int qty; //todo: what about grams?
    public BigDecimal price;
    public BigDecimal discountValue; //todo: change to discount or sth to have it minus as a record on recipt
    //todo: or maybe each product has discounted value
    public CartProductDiscount discount;//todo: make list, and rename

    public CartProduct(Product product, int qty, CartProductDiscount discount){
        this.product = product;
        this.qty = qty;
        this.discount = discount;
        this.discountValue = BigDecimal.ZERO;
        this.price = product.price.multiply(new BigDecimal(qty));//todo: refactor this
    }

    public BigDecimal getPrice(){
        return price;
    }
    public BigDecimal calculatePrice(){
        return price.multiply(BigDecimal.valueOf(qty));
    }

    public BigDecimal calculateDiscountedPrice(){
        //todo: choose the best discount
        //DiscountedPrice = Discount.CalculateDiscountPrice();
        //todo: what about different type of bread
        return discountValue;
    }

   // public int AdjustQty(int diffQty){
     //   Qty = Qty + diffQty;
        //cannot be less then 0
   // }

    //todo: up/low qty add one, remove one, set to fix number

 //   public void CheckForDiscounts(){
        //todo: check all discounts for product Id or for CartProduct
   //     if(Discount != null && !Discount.Applied){ //make list chek if any on list

   //     }
        //todo: check if applied to other products in basket
        //todo: check if condition met
        //todo: apply if needed per CartProduct
        //todo: return CartProducts(s)
  //  }
}
