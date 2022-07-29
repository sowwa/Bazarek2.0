package common.models.discounts;

import common.models.shop.CartProduct;
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
    //todo: when in case where different products applied?
    @Override
    public boolean checkIfApplies(List<CartProduct> discountedProducts){

        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));

        return qty % requiredQty == 0;
    }
    //version when price must be this same
    @Override
    public BigDecimal calculateDiscountPrice(List<CartProduct> discountedProducts) {
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        var multiplier = (int) Math.floor(qty / requiredQty) ;
        var wholeDiscountedPrice = fixedPrice.multiply(new BigDecimal(multiplier));

        var discountedItemsQty = new BigDecimal(multiplier * requiredQty);
        var itemsToDiscount = new BigDecimal(multiplier * requiredQty);
        var fullPrice = discountedProducts.stream().map(p -> p.price).reduce(BigDecimal.ZERO, BigDecimal::add);
       // var priceAfterDiscount = wholeDiscountedPrice.add(new BigDecimal(qty).subtract(discountedItemsQty).multiply())
        var fullDiscount = fixedPrice.multiply(new BigDecimal(-multiplier)).multiply(discountedProducts.get(0).product.price);//wholeDiscountedPrice.subtract(fullPrice);
        var fullDiscount2 = fixedPrice.multiply(new BigDecimal(-multiplier)).multiply(discountedProducts.get(0).product.price);//wholeDiscountedPrice.subtract(fullPrice);
        for(var product : discountedProducts){
            var thisQtyToDiscount = BigDecimal.ZERO;

            if(itemsToDiscount.compareTo(new BigDecimal(product.qty)) == 1 ||
                    itemsToDiscount.compareTo(new BigDecimal(product.qty)) == 0){
                thisQtyToDiscount = new BigDecimal(product.qty);
            } else
            if(itemsToDiscount.compareTo(new BigDecimal(product.qty)) == -1){
                thisQtyToDiscount = itemsToDiscount;
            }

            MathContext mc=new MathContext(2, RoundingMode.DOWN);
            var currentProdDisc = thisQtyToDiscount.divide(discountedItemsQty,mc).multiply(fullDiscount);
            itemsToDiscount = itemsToDiscount.subtract(thisQtyToDiscount);
            if(itemsToDiscount == BigDecimal.ZERO){
                product.discountValue = fullDiscount2;
                product.discount.applied = true;
                break;
            }
            fullDiscount2 = fullDiscount2.subtract(currentProdDisc);

            product.discountValue = currentProdDisc;
            product.discount.applied = true;
            //todo: what if some products left?
            if(itemsToDiscount == BigDecimal.ZERO)
                break;
        }
        return fixedPrice;
    }

    public BigDecimal calculateDiscountPrice3(List<CartProduct> discountedProducts) {
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        var multiplier = (int) Math.floor(qty / requiredQty) ;
        var wholeDiscountedPrice = fixedPrice.multiply(new BigDecimal(multiplier));

        var discountedItemsQty = new BigDecimal(multiplier * requiredQty);
        var itemsToDiscount = new BigDecimal(multiplier * requiredQty);
        var fullPrice = discountedProducts.stream().map(p -> p.price).reduce(BigDecimal.ZERO, BigDecimal::add);
        var fullDiscount = fixedPrice.multiply(new BigDecimal(multiplier));//wholeDiscountedPrice.subtract(fullPrice);
        var fullDiscount2 = fixedPrice.multiply(new BigDecimal(multiplier));//wholeDiscountedPrice.subtract(fullPrice);
        for(var product : discountedProducts){
            var thisQtyToDiscount = BigDecimal.ZERO;

            if(itemsToDiscount.compareTo(new BigDecimal(product.qty)) == 1 ||
                    itemsToDiscount.compareTo(new BigDecimal(product.qty)) == 0){
                thisQtyToDiscount = new BigDecimal(product.qty);
            } else
            if(itemsToDiscount.compareTo(new BigDecimal(product.qty)) == -1){
                thisQtyToDiscount = itemsToDiscount;
            }

            MathContext mc=new MathContext(2, RoundingMode.DOWN);
            var currentProdDisc = thisQtyToDiscount.divide(discountedItemsQty,mc).multiply(fullDiscount);
            itemsToDiscount = itemsToDiscount.subtract(thisQtyToDiscount);
            if(itemsToDiscount == BigDecimal.ZERO){
                product.discountValue = fullDiscount2;
                product.discount.applied = true;
                break;
            }
            fullDiscount2 = fullDiscount2.subtract(currentProdDisc);

            product.discountValue = currentProdDisc;
            product.discount.applied = true;
            //todo: what if some products left?
            if(itemsToDiscount == BigDecimal.ZERO)
                break;
        }
        return fixedPrice;
    }
    public BigDecimal calculateDiscountPrice2(List<CartProduct> discountedProducts) {
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        var multiplier = (int) Math.floor(qty / requiredQty) ;
        var wholeDiscountedPrice = fixedPrice.multiply(new BigDecimal(multiplier));
        for (var product: discountedProducts) {
            var rest = product.qty % requiredQty;

            product.discountValue = fixedPrice.subtract(product.product.price.multiply(BigDecimal.valueOf(multiplier*requiredQty)));//tutj zmienic na tylko multiply
            product.discount.applied = true;
            wholeDiscountedPrice = wholeDiscountedPrice.add(product.discountValue);

            if(rest != 0 && wholeDiscountedPrice != BigDecimal.valueOf(0)){
                while(wholeDiscountedPrice !=  BigDecimal.valueOf(0)){
                    product.discountValue.add(fixedPrice.subtract(product.product.price));
                    wholeDiscountedPrice = wholeDiscountedPrice.subtract(BigDecimal.valueOf(1));
                }

            }
            if(wholeDiscountedPrice.equals(new BigDecimal(0))){
                break;
            }
        }

        return fixedPrice;
    }

    public BigDecimal calculateDiscountPricea(List<CartProduct> discountedProducts) {
        var qty = discountedProducts.stream().map(p -> p.qty).collect(Collectors.summingInt(Integer::intValue));
        var multiplier = (int) Math.floor(qty / requiredQty) ;
        var wholeDiscountedPrice = fixedPrice.multiply(new BigDecimal(multiplier));

        if(wholeDiscountedPrice == BigDecimal.ZERO){
            return BigDecimal.ZERO;
        }


        for (var product: discountedProducts) {
            var rest = product.qty % requiredQty;
            var multiplierCurr = (int) Math.floor(product.qty / requiredQty) ;
            if(wholeDiscountedPrice.compareTo(new BigDecimal(requiredQty)) == -1 ){
                product.discountValue = fixedPrice.subtract(product.product.price.multiply(BigDecimal.valueOf(multiplierCurr * requiredQty)));//tutj zmienic na tylko multiply
                product.discount.applied = true;
                wholeDiscountedPrice = wholeDiscountedPrice.subtract(product.product.price.multiply(BigDecimal.valueOf(multiplierCurr * requiredQty)).add(product.discountValue));
            }else {
                product.discountValue = fixedPrice.subtract(product.product.price.multiply(BigDecimal.valueOf(multiplierCurr * requiredQty)));//tutj zmienic na tylko multiply
                product.discount.applied = true;
                wholeDiscountedPrice = wholeDiscountedPrice.subtract(product.product.price.multiply(BigDecimal.valueOf(multiplierCurr * requiredQty)).add(product.discountValue));
            }
            if(rest != 0 && wholeDiscountedPrice != BigDecimal.valueOf(0)){
                while(wholeDiscountedPrice !=  BigDecimal.valueOf(0) && rest != 0){
                    product.discountValue.add(fixedPrice.subtract(product.product.price));
                    wholeDiscountedPrice = wholeDiscountedPrice.subtract(BigDecimal.valueOf(1));
                    rest--;
                }

            }
            if(wholeDiscountedPrice.equals(new BigDecimal(0))){
                break;
            }
        }

        return fixedPrice;
    }

    @Override
    public BigDecimal removeDiscount(List<CartProduct> discountedProducts) {
        for (var product: discountedProducts) {
            product.discountValue = null;
            product.discount.applied = false;
        }

        return null;
    }
}
