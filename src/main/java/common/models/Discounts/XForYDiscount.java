package common.models.Discounts;

import common.models.Product;
import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class XForYDiscount extends Discount {
    public int X;
    public int Y;

    public XForYDiscount(List<Integer> ProductsIds, Unit ProductUnit) {
        super(ProductsIds, ProductUnit);
    }
//discountList.stream().filter(d -> d.ProductsIds.contains(b1.Id)).findFirst().orElse(null);
    //todo: or change it to calculate % off and apply to all products
    @Override
    public BigDecimal CalculateDiscountPrice(List<CartProduct> discountedProducts) {
        discountedProducts = (ArrayList<CartProduct>) discountedProducts.stream().sorted(Comparator.comparing(CartProduct::GetPrice)).collect(Collectors.toList());//todo: refactor this
        //todo: add checking date
        var toDiscout = X-Y;
        for (var product:discountedProducts) {
            for(int i=0;i<product.Qty;i++){
                if(toDiscout == 0){
                    break;
                }
                product.DiscountedPrice = product.DiscountedPrice.subtract(product.Product.Price);
                product.Discount.Applied = true;
                toDiscout--;
            }
        }
        //todo: lowest for free
        return null;
    }

    //todo: same method in all discounts think about refactoring
    @Override
    public BigDecimal RemoveDiscount(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.DiscountedPrice = null;
            product.Discount.Applied = false;
        }
        return null;
    }
}
