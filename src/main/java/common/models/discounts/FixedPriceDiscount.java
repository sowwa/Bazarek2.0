package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;
import common.models.shop.OrderProductDiscount;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class FixedPriceDiscount extends Discount{
    private BigDecimal fixedPrice;
    private int requiredQty;
    private MathContext mc;

    public FixedPriceDiscount(List<Integer> productsIds, Unit productUnit, int requiredQty, BigDecimal fixedPrice, String name) {
        super(productsIds, productUnit, name);
        this.requiredQty = requiredQty;
        this.fixedPrice = fixedPrice;
        this.mc = new MathContext(2, RoundingMode.DOWN);
    }

    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        var discountableQty = (Integer) discountedProducts.stream()
                .map(p -> p.getQty())
                .mapToInt(Integer::intValue).sum();
        if(requiredQty == 0)
            return true; //todo: make sure no exception, or make sure req qty musyt be at least 1

        return discountableQty / requiredQty >= 1;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        var discontableProductsQty = (Integer) discountedProducts.stream()
                .map(p -> p.getQty())
                .mapToInt(Integer::intValue).sum();

        var discountMult = (int) Math.floor(discontableProductsQty / requiredQty) ;

        if(discountMult == 0)
            return;

        var discountedPrice = fixedPrice.multiply(new BigDecimal(discountMult)); //todo: can fixed proce be 0
        var remainingDiscountedPrice = fixedPrice.multiply(new BigDecimal(discountMult));

        var discountedProductsQty = new BigDecimal(discountMult * requiredQty);
        var productsLeftToDiscountQty = new BigDecimal(discountMult * requiredQty);

        for(var product : discountedProducts){

            var productQty = new BigDecimal(product.getQty());

            var productQtyToDiscount = GetProductQtyToDiscount(productsLeftToDiscountQty, productQty);
            var notDiscountedQtyPrice = productQty
                    .subtract(productQtyToDiscount)
                    .multiply(product.getProduct().getPrice());
            var discountedQtyPrice = productsLeftToDiscountQty.subtract(productQtyToDiscount).equals(BigDecimal.ZERO)
                    ? remainingDiscountedPrice
                    : productQtyToDiscount.divide(discountedProductsQty,mc).multiply(discountedPrice);

            var productPrice = notDiscountedQtyPrice.add(discountedQtyPrice);

            product.setDiscountValue(product.getPrice()
                    .subtract(productPrice)
                    .multiply(new BigDecimal(-1)));
            product.setDiscount(new OrderProductDiscount(this.name,true));

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
