package proccessing;

import common.interfaces.IDiscountService;
import common.models.discounts.Discount;
import common.models.shop.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class DiscountService implements IDiscountService {
    private List<Discount> discounts;

    public DiscountService(){
        this.discounts = new ArrayList<>();
    }

    public void addDiscount(Discount discount){
        discounts.add(discount);
    }
    @Override
    public void ApplyDiscounts(List<OrderProduct> orderProducts) {

        var validDiscounts = discounts.stream()
                .filter(d -> orderProducts.stream()
                       .anyMatch(p -> d.getProductsIds().contains(p.getProduct().getId())))
                .toList();

        for (var discount: validDiscounts) {
            var products = orderProducts.stream().filter(p -> discount.getProductsIds().contains(p.getProduct().getId())).toList();

            if(discount.checkIfApplies(products)){
                discount.calculateDiscountPrice(products);
            }
        }
    }
}
