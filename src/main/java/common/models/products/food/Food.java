package common.models.products.food;

import common.models.products.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Food extends Product {
    public int weight; //in grams so int for now not necessary or sth
    public Food(String name, BigDecimal price, LocalDate creationDate, int weight) {
        super(name, price, creationDate);
        this.weight = weight;
    }
}
