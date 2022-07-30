package common.models.products.food;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Vegetable extends Food{
    public Vegetable(String name, BigDecimal price, LocalDate creationDate, int weight) {
        super(name, price, creationDate, weight);
    }
}
