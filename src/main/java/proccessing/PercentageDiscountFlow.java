package proccessing;

import common.interfaces.IDiscountFlow;
import common.models.discounts.PercentageDiscount;
import common.models.shop.OrderProduct;

import java.util.List;

public class PercentageDiscountFlow implements IDiscountFlow<PercentageDiscount> {
    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts) {
        return false;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {

    }
}
