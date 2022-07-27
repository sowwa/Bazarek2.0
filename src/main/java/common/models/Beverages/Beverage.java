package common.models.Beverages;

import common.models.Product;

import java.math.BigDecimal;

public abstract class Beverage extends Product {

    public Beverage(String Name, BigDecimal Price) {
        super(Name, Price);
    }
}
