package common.models.beverages;

import common.models.Product;

import java.math.BigDecimal;
import java.util.Date;

public class BeveragePack<T extends Beverage> extends Product {
    //todo: make sure T is of "final" type - only one id - check in method
    public int qty;
    public T beverage;

    public BeveragePack(String name, BigDecimal price, int qty, T beverage, Date expirationDate){
        super(name, price, expirationDate);
        this.beverage = beverage;
        this.qty = qty;
    }

}

