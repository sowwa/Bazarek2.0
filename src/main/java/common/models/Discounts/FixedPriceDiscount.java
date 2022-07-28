package common.models.Discounts;

import common.models.Product;
import common.models.Shop.CartProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FixedPriceDiscount extends Discount{
    public BigDecimal FixedPrice;
    public int RequiredQty;

    public FixedPriceDiscount(List<Integer> ProductsIds, Unit ProductUnit, int RequiredQty) {
        super(ProductsIds, ProductUnit);
        this.RequiredQty = RequiredQty;
    }

    @Override
    public BigDecimal CalculateDiscountPrice(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.DiscountedPrice = FixedPrice;
            product.Discount.Applied = true;
        }
        return FixedPrice;
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
