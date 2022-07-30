package proccessing;

import common.interfaces.IDiscountFlow;
import common.models.discounts.FixedPriceDiscount;
import common.models.shop.OrderProduct;

import java.util.List;

public class FixedPriceDiscountFlow implements IDiscountFlow<FixedPriceDiscount> {

    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts) {
        return false;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {

    }
}
