package common.models.Shop;

import common.models.Discounts.Discount;
import common.models.Product;

import java.math.BigDecimal;

public class CartProduct {
    public Product Product;
    public int Qty; //todo: what about grams?
    public BigDecimal Price;
    public BigDecimal DiscountedPrice;
    public CartProductDiscount Discount;//todo: make list, and make separate class CartDiscout with false true for apply

    public CartProduct(Product Product, int Qty, CartProductDiscount Discount){
        this.Product = Product;
        this.Qty = Qty;
        this.Discount = Discount;
    }

    public BigDecimal GetPrice(){
        return Price;
    }
    public BigDecimal CalculatePrice(){
        return Price.multiply(BigDecimal.valueOf(Qty));
    }

    public BigDecimal CalculateDiscountedPrice(){
        //todo: choose the best discount
        //DiscountedPrice = Discount.CalculateDiscountPrice();
        //todo: what about different type of bread
        return DiscountedPrice;
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
