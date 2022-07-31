package common.models.products.beverages;

import common.models.enums.Unit;
import common.models.products.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BeveragePack<T extends Beverage> extends Product { //todo: could be more generic
    //todo: make sure T is of "final" type - only one id - check in method
    private int qty;
    private T beverage;

    public BeveragePack(String name, BigDecimal price, int qty, T beverage, LocalDate creationDate, Unit unit){
        super(name, price, creationDate, unit);
        this.beverage = beverage;
        this.qty = qty;
    }

}

