package common.models.products.food;

import common.models.products.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Food extends Product {
    public Food(String name, BigDecimal price, LocalDate creationDate) {
        super(name, price, creationDate);
    }
}
