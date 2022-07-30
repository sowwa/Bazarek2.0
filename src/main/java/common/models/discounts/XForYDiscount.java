package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        var discountableQty = (Integer) discountedProducts.stream()
                .filter(p -> LocalDate.now().isEqual(p.product.creationDate.plusDays(daysOld)))
                .map(p -> p.qty)
                .mapToInt(Integer::intValue).sum();

        return discountableQty >= xQty;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        discountedProducts = discountedProducts.stream()
                .filter(p -> LocalDate.now().isEqual(p.product.creationDate.plusDays(daysOld)))
                .sorted(Comparator.comparing(OrderProduct::getPrice))
                .collect(Collectors.toList());

        var discountableQty = discountedProducts.stream()
                .map(p -> p.qty)
                .mapToInt(Integer::intValue).sum();

        var multiplier = (int) Math.floor(discountableQty / xQty);
        var qtyToDiscount = (xQty - yQty)*multiplier;

        for (var product:discountedProducts) {
            var productQty = product.qty;

            if(qtyToDiscount > productQty){
                product.discountValue = product.discountValue.add(product.price.multiply(new BigDecimal(-1)));
                product.discount.applied = true;

                qtyToDiscount = qtyToDiscount - productQty;
            }  else {
                product.discountValue = product.discountValue.add(product.product.price.multiply(new BigDecimal(qtyToDiscount*-1)));
                product.discount.applied = true;

                break;
            }
        }
    }

}
