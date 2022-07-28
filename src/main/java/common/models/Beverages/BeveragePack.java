package common.models.Beverages;

import common.models.Product;

import java.math.BigDecimal;

public class BeveragePack<T extends Beverage> extends Product {
    //todo: make sure T is of "final" type - only one id - check in method
    public int qty;
    public T beverage;

    public BeveragePack(String name, BigDecimal price, int qty, T beverage){
        super(name, price);
        this.beverage = beverage;
        this.qty = qty;
    }

}

