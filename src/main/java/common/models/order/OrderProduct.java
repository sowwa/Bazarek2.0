package common.models.order;

import common.models.products.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderProduct {
    private Product product;
    private int qty;
    private BigDecimal price;
    private OrderProductDiscount discount;

    public OrderProduct(Product product, int qty, OrderProductDiscount discount){
        this.product = product;
        this.setQty(qty);
        this.discount = discount;
    }
    public int getId(){return this.product.getId();}
    public Product getProduct(){return this.product;}
    public int getQty(){return this.qty;}
    public void setQty(int qty){
        if(qty >= 0){
            this.qty = qty;
            this.calculatePrice();
        }
        else
            throw new IllegalArgumentException("Quantity can't be a negative number.");
    }
    public BigDecimal getPrice(){return price;}
    public void setPrice(BigDecimal price){this.price = price; calculatePrice();}
    public OrderProductDiscount getDiscount(){return this.discount;}
    public void setDiscount(OrderProductDiscount discount){this.discount = discount;}
    public void calculatePrice(){
        this.price = this.product.getPrice().multiply(new BigDecimal(this.qty)).setScale(2, RoundingMode.DOWN);
    }
}
