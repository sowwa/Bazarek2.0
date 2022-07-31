package common.models.products.beverages;

import common.models.enums.Unit;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Alcohol extends Beverage{
    public Alcohol(String name, BigDecimal price, LocalDate creationDate, Unit unit) {
        super(name, price, creationDate, unit);
    }
}
