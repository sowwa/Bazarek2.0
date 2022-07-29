package common.models.beverages;

import common.models.Product;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Beverage extends Product {

    public Beverage(String name, BigDecimal price, Date expirationDate) {
        super(name, price, expirationDate);
    }
}
