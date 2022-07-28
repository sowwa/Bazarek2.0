package common.models.Shop;

import common.models.Discounts.Discount;

public class CartProductDiscount {
    public common.models.Discounts.Discount Discount;
    public boolean Applied;

    public CartProductDiscount(Discount Discount, boolean Applied){
        this.Discount = Discount;
        this.Applied = Applied;
    }
}
