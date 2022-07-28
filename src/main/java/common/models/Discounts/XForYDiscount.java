package common.models.Discounts;

import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class XForYDiscount extends Discount {
    public int xCount;
    public int yCount;

    public XForYDiscount(List<Integer> ProductsIds, Unit ProductUnit) {
        super(ProductsIds, ProductUnit);
    }
//discountList.stream().filter(d -> d.ProductsIds.contains(b1.Id)).findFirst().orElse(null);
    //todo: or change it to calculate % off and apply to all products

    @Override
    public boolean checkIfApplies(List<CartProduct> discountedProducts){
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        //todo: what about multification?
        return qty >= xCount;
    }

    @Override
    public BigDecimal calculateDiscountPrice(List<CartProduct> discountedProducts) {
        discountedProducts = (ArrayList<CartProduct>) discountedProducts.stream().sorted(Comparator.comparing(CartProduct::getPrice)).collect(Collectors.toList());//todo: refactor this
        //todo: add checking date
        var toDiscout = xCount - yCount;
        for (var product:discountedProducts) {
            for(int i = 0; i<product.qty; i++){
                if(toDiscout == 0){
                    break;
                }
                product.discountedPrice = product.discountedPrice.subtract(product.product.price);
                product.discount.applied = true;
                toDiscout--;
            }
        }
        //todo: lowest for free
        return null;
    }

    //todo: same method in all discounts think about refactoring
    @Override
    public BigDecimal removeDiscount(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.discountedPrice = null;
            product.discount.applied = false;
        }
        return null;
    }
}
