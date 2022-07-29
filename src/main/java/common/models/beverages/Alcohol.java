package common.models.beverages;

import java.math.BigDecimal;
import java.util.Date;

public class Alcohol extends Beverage{
    public Alcohol(String name, BigDecimal price, Date expirationDate) {
        super(name, price, expirationDate);
    }
}
