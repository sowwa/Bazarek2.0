package common.models.products.beverages;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Alcohol extends Beverage{
    public Alcohol(String name, BigDecimal price, LocalDate creationDate) {
        super(name, price, creationDate);
    }
}
