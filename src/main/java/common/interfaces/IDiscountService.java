package common.interfaces;

import common.models.discounts.Discount;
import common.models.order.OrderProduct;

import java.util.List;

public interface IDiscountService {
    void applyDiscounts(List<OrderProduct> orderProducts);
    Discount addDiscount(Discount discount);
    List<Discount> getDiscounts();
    default void removeDiscounts(List<OrderProduct> orderProducts){
        for (var product: orderProducts) {
            product.setDiscount(null);
        }
    }
}
