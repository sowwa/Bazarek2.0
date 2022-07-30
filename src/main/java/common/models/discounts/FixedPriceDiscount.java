package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class FixedPriceDiscount extends Discount{
    public BigDecimal fixedPrice;
    public int requiredQty;

    public FixedPriceDiscount(List<Integer> productsIds, Unit productUnit, int requiredQty, BigDecimal fixedPrice) {
        super(productsIds, productUnit);
        this.requiredQty = requiredQty;
        this.fixedPrice = fixedPrice;
    }

    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        var discountableQty = (Integer) discountedProducts.stream()
                .map(p -> p.qty)
                .mapToInt(Integer::intValue).sum();
        if(requiredQty == 0)
            return true; //todo: make sure no exception, or make sure req qty musyt be at least 1

        return discountableQty / requiredQty >= 1;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        var discontableProductsQty = (Integer) discountedProducts.stream()
                .map(p -> p.qty)
                .mapToInt(Integer::intValue).sum();

        var discountMult = (int) Math.floor(discontableProductsQty / requiredQty) ;

        if(discountMult == 0)
            return;

        var discountedPrice = fixedPrice.multiply(new BigDecimal(discountMult)); //todo: can fixed proce be 0
        var remainingDiscountedPrice = fixedPrice.multiply(new BigDecimal(discountMult));

        var discountedProductsQty = new BigDecimal(discountMult * requiredQty);
        var productsLeftToDiscountQty = new BigDecimal(discountMult * requiredQty);

        for(var product : discountedProducts){
            var mc=new MathContext(2, RoundingMode.DOWN);//todo: make it in class
            var productQty = new BigDecimal(product.qty);

            var productQtyToDiscount = GetProductQtyToDiscount(productsLeftToDiscountQty, productQty);
            var notDiscountedQtyPrice = productQty
                    .subtract(productQtyToDiscount)
                    .multiply(product.product.price);
            var discountedQtyPrice = productsLeftToDiscountQty.subtract(productQtyToDiscount).equals(BigDecimal.ZERO)
                    ? remainingDiscountedPrice
                    : productQtyToDiscount.divide(discountedProductsQty,mc).multiply(discountedPrice);

            var productPrice = notDiscountedQtyPrice.add(discountedQtyPrice);

            product.discountValue = product.price
                    .subtract(productPrice)
                    .multiply(new BigDecimal(-1));
            product.discount.applied = true;

            productsLeftToDiscountQty = productsLeftToDiscountQty.subtract(productQtyToDiscount);
            remainingDiscountedPrice = remainingDiscountedPrice.subtract(discountedQtyPrice);

            if(productsLeftToDiscountQty.equals(BigDecimal.ZERO))
                break;
        }
    }

    private BigDecimal GetProductQtyToDiscount(BigDecimal productsLeftToDiscountQty, BigDecimal productQty){
        return productsLeftToDiscountQty.compareTo(productQty) < 0
                ? productsLeftToDiscountQty
                : productQty;
    }
}
