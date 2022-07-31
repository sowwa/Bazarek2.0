package common.interfaces;

import common.models.shop.OrderProduct;

import java.util.List;

public interface IDiscountService {

    void ApplyDiscounts(List<OrderProduct> orderProducts);
    default void removeDiscount(List<OrderProduct> discountedProducts){
        for (var product: discountedProducts) {
            product.setDiscountValue(null);
            product.setDiscount(null);
        }
    }
}
