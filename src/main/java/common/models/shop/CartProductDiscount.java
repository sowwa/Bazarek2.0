package common.models.shop;

import common.models.discounts.Discount;

public class CartProductDiscount {
    public common.models.discounts.Discount discount;
    public boolean applied;

    public CartProductDiscount(Discount discount, boolean applied){
        this.discount = discount;
        this.applied = applied;
    }
}
