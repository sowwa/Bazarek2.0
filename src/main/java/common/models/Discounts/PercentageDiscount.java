package common.models.Discounts;

import common.models.Product;
import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//todo: think about grams vs unit
public class PercentageDiscount extends Discount{
    public int MinQty; //todo: maybe change int to some other type
    public int MaxQty;
    public BigDecimal PercentageOff; //todo: change to other type
    public PercentageDiscount(List<Integer> ProductsIds, Unit ProductUnit) {
        super(ProductsIds, ProductUnit);
    }

    @Override
    public BigDecimal CalculateDiscountPrice(List<CartProduct> discountedProducts) {
        for (var product : discountedProducts) {
            var discountedPrice = product.Price.multiply(PercentageOff);
            product.DiscountedPrice = discountedPrice;
            product.Discount.Applied = true;
        }
        //todo: return some other type
        return null;
    }

    @Override
    public BigDecimal RemoveDiscount(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.DiscountedPrice = null;
            product.Discount.Applied = false;
        }
        return null;
    }
}
