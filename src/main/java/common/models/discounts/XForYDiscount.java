package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;
import common.models.shop.OrderProductDiscount;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class XForYDiscount extends Discount {
    private int xQty;
    private int yQty;
    private int daysOld;

    public XForYDiscount(List<Integer> ProductsIds, Unit ProductUnit, int xQty, int yQty, int daysOld, String name) {
        super(ProductsIds, ProductUnit, name);
        this.xQty = xQty;
        this.yQty = yQty;
        this.daysOld = daysOld;
    }

    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        var discountableQty = (Integer) discountedProducts.stream()
                .filter(p -> LocalDate.now().isEqual(p.getProduct().getCreationDate().plusDays(daysOld)))
                .map(p -> p.getQty())
                .mapToInt(Integer::intValue).sum();

        return discountableQty >= xQty;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        discountedProducts = discountedProducts.stream()
                .filter(p -> LocalDate.now().isEqual(p.getProduct().getCreationDate().plusDays(daysOld)))
                .sorted(Comparator.comparing(OrderProduct::getPrice))
                .collect(Collectors.toList());

        var discountableQty = discountedProducts.stream()
                .map(p -> p.getQty())
                .mapToInt(Integer::intValue).sum();

        var multiplier = (int) Math.floor(discountableQty / xQty);
        var qtyToDiscount = (xQty - yQty)*multiplier;
        var mc = new MathContext(3);
        for (var product:discountedProducts) {
            var productQty = product.getQty();

            if(qtyToDiscount > productQty){
                product.setDiscountValue(product.getDiscountValue().add(product.getPrice().multiply(new BigDecimal(-1), mc)));
                product.setDiscount(new OrderProductDiscount(this.name,true));

                qtyToDiscount = qtyToDiscount - productQty;
            }  else {
                product.setDiscountValue(product.getDiscountValue().add(product.getProduct().getPrice().multiply(new BigDecimal(qtyToDiscount*-1), mc)));
                product.setDiscount(new OrderProductDiscount(this.name,true));

                break;
            }
        }
    }

}
