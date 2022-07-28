package common.models.Shop;

import common.models.Discounts.Discount;

public class CartProductDiscount {
    public common.models.Discounts.Discount discount;
    public boolean applied;

    public CartProductDiscount(Discount discount, boolean applied){
        this.discount = discount;
        this.applied = applied;
    }
}
