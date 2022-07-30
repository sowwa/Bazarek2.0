package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class XForYDiscount extends Discount {
    public int xCount;
    public int yCount;

    public XForYDiscount(List<Integer> ProductsIds, Unit ProductUnit, int xCount, int yCount) {
        super(ProductsIds, ProductUnit);
        this.xCount = xCount;
        this.yCount = yCount;
    }
//discountList.stream().filter(d -> d.ProductsIds.contains(b1.Id)).findFirst().orElse(null);
    //todo: or change it to calculate % off and apply to all products

    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        //todo: what about multification?
        return qty >= xCount;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        discountedProducts = (ArrayList<OrderProduct>) discountedProducts.stream().sorted(Comparator.comparing(OrderProduct::getPrice)).collect(Collectors.toList());//todo: refactor this
        var qty = (Integer) discountedProducts.stream().map(p -> p.qty).mapToInt(Integer::intValue).sum();
        var multiplier = (int) Math.floor(qty / xCount) ;
        //todo: add checking date
        var toDiscout = (xCount - yCount)*multiplier;
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
