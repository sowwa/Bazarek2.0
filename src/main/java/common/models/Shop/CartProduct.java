package common.models.Shop;

import common.models.Discounts.Discount;
import common.models.Product;

public class CartProduct {
    public Product Product;
    public int Qty; //todo: what about grams?
    public Discount Discount;

    public CartProduct(Product Product, int Qty, Discount Discount){
        this.Product = Product;
        this.Qty = Qty;
        this.Discount = Discount;
    }

}
