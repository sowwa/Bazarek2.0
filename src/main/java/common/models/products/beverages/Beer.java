package common.models.products.beverages;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Beer extends Alcohol {
    public Beer(String name, BigDecimal price, LocalDate creationDate) {
        super(name, price, creationDate);
    }
}
