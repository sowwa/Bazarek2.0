package common.models.products.beverages;

import common.models.products.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Beverage extends Product {

    public Beverage(String name, BigDecimal price, LocalDate creationDate) {
        super(name, price, creationDate);
    }
}
