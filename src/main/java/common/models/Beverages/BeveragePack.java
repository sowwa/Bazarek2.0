package common.models.Beverages;

import common.models.Product;

import java.math.BigDecimal;

public class BeveragePack<T extends Beverage> extends Product {
    //todo: make sure T is of "final" type - only one id - check in method
    public int Qty;
    public T Beverage;

    public BeveragePack(String Name, BigDecimal Price, int Qty, T Beverage){
        super(Name, Price);
        this.Beverage = Beverage;
        this.Qty = Qty;
    }

}

