package common.models.products.beverages;

import common.models.products.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BeveragePack<T extends Beverage> extends Product {
    private int qty;
    private T beverage;

    public BeveragePack(String name, BigDecimal price, int qty, T beverage, LocalDate creationDate){
        super(name, price, creationDate);
        this.beverage = beverage;
        this.qty = qty;
    }

}

