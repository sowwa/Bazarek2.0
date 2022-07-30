package common.models.products.food;

import java.math.BigDecimal;
import java.util.Date;

public class Bread extends Food{
    public Bread(String name, BigDecimal price, Date expirationDate, int weight) {
        super(name, price, expirationDate, weight);
    }
}
