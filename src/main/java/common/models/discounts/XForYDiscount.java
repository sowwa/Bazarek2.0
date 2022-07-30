package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class XForYDiscount extends Discount {
    public int xQty;
    public int yQty;
    public int daysOld;//todo: co z przedziałem?

    public XForYDiscount(List<Integer> ProductsIds, Unit ProductUnit, int xQty, int yQty, int daysOld) {
        super(ProductsIds, ProductUnit);
        this.xQty = xQty;
        this.yQty = yQty;
        this.daysOld = daysOld;
    }
//discountList.stream().filter(d -> d.ProductsIds.contains(b1.Id)).findFirst().orElse(null);
    //todo: or change it to calculate % off and apply to all products

    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        var discountableQty = discountedProducts.stream()
                .filter(p -> LocalDate.now().isEqual(p.product.creationDate.plusDays(daysOld)))
                .map(p -> p.qty)
                .collect(Collectors.summingInt(Integer::intValue));

        return discountableQty >= xQty;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        discountedProducts = discountedProducts.stream().sorted(Comparator.comparing(OrderProduct::getPrice)).collect(Collectors.toList());//todo: refactor this

        var qty = (Integer) discountedProducts.stream()
                .map(p -> p.qty)
                .mapToInt(Integer::intValue).sum();

        var multiplier = (int) Math.floor(qty / xQty) ;
        var toDiscout = (xQty - yQty)*multiplier;
        for (var product:discountedProducts) {
            for(int i = 0; i<product.qty; i++){
                if(toDiscout == 0){
                    break;
                }
                product.discountValue = product.discountValue.add(product.product.price.multiply(new BigDecimal(-1)));
                product.discount.applied = true;
                toDiscout--;
            }
        }
    }

}
