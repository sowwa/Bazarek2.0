package common.models.shop;

import common.models.discounts.Discount;

public class OrderProductDiscount {
    public common.models.discounts.Discount discount;
    public boolean applied;

    public OrderProductDiscount(Discount discount, boolean applied){
        this.discount = discount;
        this.applied = applied;
    }
}
