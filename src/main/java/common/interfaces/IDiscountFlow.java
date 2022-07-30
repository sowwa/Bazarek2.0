package common.interfaces;

import common.models.discounts.Discount;
import common.models.products.beverages.Beverage;
import common.models.shop.OrderProduct;

import java.util.List;

public interface IDiscountFlow <T extends Discount>{
    boolean checkIfApplies(List<OrderProduct> discountedProducts);
    void calculateDiscountPrice(List<OrderProduct> discountedProducts);
    default void removeDiscount(List<OrderProduct> discountedProducts){
        for (var product: discountedProducts) {
            product.discountValue = null;
            product.discount.applied = false;
        }
    }
}
