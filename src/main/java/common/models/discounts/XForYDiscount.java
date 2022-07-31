package common.models.discounts;

import common.models.order.OrderProduct;
import common.models.order.OrderProductDiscount;

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

    public XForYDiscount(List<Integer> ProductsIds, int xQty, int yQty, int daysOld, String name) {
        super(ProductsIds, name);
        this.setXQty(xQty);//todo: rename x and y to more readable
        this.setYQty(yQty);
        this.setDaysOld(daysOld);
    }
    public void setXQty(int xQty){
        if(xQty > 0)
            this.xQty = xQty;
        else throw new IllegalArgumentException("X quantity can't be less then 1.");
    }
    public void setYQty(int yQty){
        if(yQty > 0)
            this.yQty = yQty;
        else throw new IllegalArgumentException("Y quantity can't be less then 1.");
    }
    public void setDaysOld(int daysOld){
        if(daysOld >= 0)
            this.daysOld = daysOld;
        else throw new IllegalArgumentException("Days old can't be negative.");
    }
    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        var discountableQty = (Integer) discountedProducts.stream()
                .filter(p -> LocalDate.now().isEqual(p.getProduct().getCreationDate().plusDays(daysOld)))
                .map(OrderProduct::getQty)
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
                .map(OrderProduct::getQty)
                .mapToInt(Integer::intValue).sum();

        var multiplier = (int) Math.floor(discountableQty / (float)xQty);
        var qtyToDiscount = (xQty - yQty)*multiplier;
        var mc = new MathContext(3);

        for (var product:discountedProducts) {
            var productQty = product.getQty();

            if(qtyToDiscount > productQty){
                product.setDiscount(new OrderProductDiscount(this.name, product.getPrice().multiply(new BigDecimal(-1), mc)));
                qtyToDiscount = qtyToDiscount - productQty;
            }  else {
                product.setDiscount(new OrderProductDiscount(this.name, product.getProduct().getPrice().multiply(new BigDecimal(qtyToDiscount*-1), mc)));
                break;
            }
        }
    }

}
