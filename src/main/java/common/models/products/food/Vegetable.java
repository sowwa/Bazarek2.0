package common.models.products.food;

import common.models.enums.Unit;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Vegetable extends Food{
    public Vegetable(String name, BigDecimal price, LocalDate creationDate, Unit unit) {
        super(name, price, creationDate, unit);
    }
}
