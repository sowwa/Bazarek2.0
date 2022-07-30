package common.models.products.beverages;

import java.math.BigDecimal;
import java.util.Date;

public class Beer extends Alcohol{
    public Beer(String name, BigDecimal price, Date expirationDate) {
        super(name, price, expirationDate);
    }
}
