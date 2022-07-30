package common.models.discounts;

import common.models.shop.OrderProduct;
import common.models.enums.Unit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

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

        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));

        return qty % requiredQty == 0;
    }
    //version when price must be this same
    @Override
    public void calculateDiscountPrice(List<OrderProduct> discountedProducts) {
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        var multiplier = (int) Math.floor(qty / requiredQty) ;
        var wholeDiscountedPrice = fixedPrice.multiply(new BigDecimal(multiplier));
        var wholeDiscountedPrice2 = fixedPrice.multiply(new BigDecimal(multiplier));

        var discountedItemsQty = new BigDecimal(multiplier * requiredQty);
        var itemsToDiscount = new BigDecimal(multiplier * requiredQty);

        for(var product : discountedProducts){
            var thisQtyToDiscount = BigDecimal.ZERO;
            var thisProdQty = new BigDecimal(product.qty);
            if(itemsToDiscount.compareTo(thisProdQty) == 1 ||
                    itemsToDiscount.compareTo(thisProdQty) == 0){
                thisQtyToDiscount = new BigDecimal(product.qty);
            } else
            if(itemsToDiscount.compareTo(thisProdQty) == -1){
                thisQtyToDiscount = itemsToDiscount;
            }
            MathContext mc=new MathContext(2, RoundingMode.DOWN);

            var productsNotOnPromotion = thisProdQty.subtract(thisQtyToDiscount);

            var nonDiscountprice = productsNotOnPromotion.multiply(product.product.price);
            var cos = thisQtyToDiscount.divide(discountedItemsQty,mc).multiply(wholeDiscountedPrice);
            if(itemsToDiscount.subtract(thisQtyToDiscount) == BigDecimal.ZERO)
                cos = wholeDiscountedPrice2;
            var sum = nonDiscountprice.add(cos);

            var wynik = product.price.subtract(sum).multiply(new BigDecimal(-1));
            itemsToDiscount = itemsToDiscount.subtract(thisQtyToDiscount);

            product.discountValue = wynik;
            product.discount.applied = true;
            //todo: what if some products left?
            wholeDiscountedPrice2 = wholeDiscountedPrice2.subtract(cos);
            if(itemsToDiscount == BigDecimal.ZERO)
                break;
        }
    }

}
