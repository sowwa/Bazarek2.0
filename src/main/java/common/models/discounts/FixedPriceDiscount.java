package common.models.discounts;

import common.models.order.OrderProduct;
import common.models.order.OrderProductDiscount;
import common.models.products.Product;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class FixedPriceDiscount extends Discount{
    private BigDecimal fixedPrice;
    private int requiredQty;
    private MathContext mc;

    public FixedPriceDiscount(List<Integer> productsIds, int requiredQty, BigDecimal fixedPrice, String name) {
        super(productsIds, name);
        this.setRequiredQty(requiredQty);
        this.setFixedPrice(fixedPrice);
        this.mc = new MathContext(4);
    }

    public void setRequiredQty(int requiredQty){
        if(requiredQty > 0)
            this.requiredQty = requiredQty;
        else throw new IllegalArgumentException("Required quantity can't be a negative number.");
    }

    public void setFixedPrice(BigDecimal fixedPrice){
        if(fixedPrice.compareTo(BigDecimal.ZERO) > 0)
            this.fixedPrice = fixedPrice;
        else throw new IllegalArgumentException("Price can't be a negative number.");
    }
    @Override
    public boolean checkIfApplies(List<OrderProduct> discountedProducts){
        var discountableQty = (Integer) discountedProducts.stream()
                .map(OrderProduct::getQty)
                .mapToInt(Integer::intValue).sum();

        return discountableQty / requiredQty >= 1;
    }

    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        var discountableProductsQty = (Integer) discountedProducts.stream()
                .map(OrderProduct::getQty)
                .mapToInt(Integer::intValue).sum();

        var discountMultiplicator = (int) Math.floor(discountableProductsQty / requiredQty) ;

        if(discountMultiplicator == 0)
            return;

        var discountedProductsPrice = fixedPrice.multiply(new BigDecimal(discountMultiplicator));
        var remainingDiscountedProductsPrice = fixedPrice.multiply(new BigDecimal(discountMultiplicator));

        var discountedProductsQty = new BigDecimal(discountMultiplicator * requiredQty);
        var productsLeftToDiscountQty = new BigDecimal(discountMultiplicator * requiredQty);

        for(var product : discountedProducts){
            if(productsLeftToDiscountQty.equals(BigDecimal.ZERO))
                break;

            var productQty = new BigDecimal(product.getQty());

            var productQtyToDiscount = getProductQtyToDiscount(productsLeftToDiscountQty, productQty);

            var notDiscountedProductQtyPrice = productQty
                    .subtract(productQtyToDiscount)
                    .multiply(product.getProduct().getPrice());
            var discountedProductQtyPrice = productsLeftToDiscountQty.subtract(productQtyToDiscount).equals(BigDecimal.ZERO)
                    ? remainingDiscountedProductsPrice
                    : productQtyToDiscount.divide(discountedProductsQty,mc).multiply(discountedProductsPrice,mc);

            var productPrice = notDiscountedProductQtyPrice.add(discountedProductQtyPrice);

            product.setDiscount(new OrderProductDiscount(this.name, product.getPrice()
                    .subtract(productPrice)
                    .multiply(new BigDecimal(-1))));

            productsLeftToDiscountQty = productsLeftToDiscountQty.subtract(productQtyToDiscount);
            remainingDiscountedProductsPrice = remainingDiscountedProductsPrice.subtract(discountedProductQtyPrice);
        }
    }

    private BigDecimal getProductQtyToDiscount(BigDecimal productsLeftToDiscountQty, BigDecimal productQty){
        return productsLeftToDiscountQty.compareTo(productQty) < 0
                ? productsLeftToDiscountQty
                : productQty;
    }
}