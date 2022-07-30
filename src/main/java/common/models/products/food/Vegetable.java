package common.models.products.food;

import java.math.BigDecimal;
import java.util.Date;

public class Vegetable extends Food{
    public Vegetable(String name, BigDecimal price, Date expirationDate, int weight) {
        super(name, price, expirationDate, weight);
    }
}
