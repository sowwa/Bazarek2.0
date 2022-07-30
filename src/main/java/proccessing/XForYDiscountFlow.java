package proccessing;

import common.interfaces.IDiscountFlow;
import common.models.discounts.XForYDiscount;
import common.models.shop.OrderProduct;

import java.util.List;

public class XForYDiscountFlow implements IDiscountFlow<XForYDiscount> {
    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts) {
        return false;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {

    }
}
