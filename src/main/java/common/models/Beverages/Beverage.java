package common.models.Beverages;

import common.models.Product;

import java.math.BigDecimal;

public abstract class Beverage extends Product {

    public Beverage(String name, BigDecimal price) {
        super(name, price);
    }
}
