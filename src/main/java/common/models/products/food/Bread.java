package common.models.products.food;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Bread extends Food {
    public Bread(String name, BigDecimal price, LocalDate creationDate) {
        super(name, price, creationDate);
    }
}
