package common.models.shop;

import common.models.products.Product;

import java.math.BigDecimal;
import java.math.MathContext;

public class OrderProduct {
    private Product product;
    private int qty;
    private BigDecimal price;
    private BigDecimal discountValue;
    private OrderProductDiscount discount;//todo: make list, and rename

    public OrderProduct(Product product, int qty, OrderProductDiscount discount){
        this.product = product;
        this.qty = qty;
        this.discount = discount;
        this.discountValue = BigDecimal.ZERO;
        this.price = product.getPrice().multiply(new BigDecimal(qty)).round(new MathContext(4)).stripTrailingZeros();//todo: refactor this
    }
    public Product getProduct(){return this.product;}
    public int getQty(){return this.qty;}
    public void setQty(int qty){this.qty = qty;}
    public BigDecimal getDiscountValue(){return discountValue;}
    public void setDiscountValue(BigDecimal discountValue){this.discountValue = discountValue.stripTrailingZeros();}
    public BigDecimal getPrice(){
        return price;
    }
    public OrderProductDiscount getDiscount(){return this.discount;}
    public void setDiscount(OrderProductDiscount discount){this.discount = discount;}
    public void calculatePrice(){
        this.price = this.product.getPrice().multiply(new BigDecimal(this.qty)).stripTrailingZeros();
    }
}
