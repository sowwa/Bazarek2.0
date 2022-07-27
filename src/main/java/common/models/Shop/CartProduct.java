package common.models.Shop;

import common.models.Discounts.Discount;
import common.models.Product;

import java.math.BigDecimal;

public class CartProduct {
    public Product Product;
    public int Qty; //todo: what about grams?
    public BigDecimal Price;
    public BigDecimal DiscountedPrice;
    public Discount Discount;

    public CartProduct(Product Product, int Qty, Discount Discount){
        this.Product = Product;
        this.Qty = Qty;
        this.Discount = Discount;
    }

    //todo: calculate price
    //todo: up/low qty
}
