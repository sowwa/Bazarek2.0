package proccessing;

import common.interfaces.IDiscountService;
import common.models.discounts.Discount;
import common.models.order.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class DiscountService implements IDiscountService {
    private List<Discount> discounts;

    public DiscountService(){
        this.discounts = new ArrayList<>();
    }

    public Discount addDiscount(Discount discount){
        if(discounts.stream().noneMatch(d -> d.getId() == discount.getId())){
            discounts.add(discount);
            return discount;
        }
        else throw new IllegalArgumentException("Discount already on list.");
    }

    @Override
    public List<Discount> getDiscounts() {
        return this.discounts;
    }

    @Override
    public void applyDiscounts(List<OrderProduct> orderProducts) {

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
