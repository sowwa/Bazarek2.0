package common.models.products.food;

import common.models.products.Product;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Food extends Product {
    public int weight; //in grams so int for now not necessary or sth
    public Food(String name, BigDecimal price, Date expirationDate, int weight) {
        super(name, price, expirationDate);
        this.weight = weight;
    }
}
