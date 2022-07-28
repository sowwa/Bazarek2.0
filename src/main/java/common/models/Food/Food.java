package common.models.Food;

import common.models.Product;

import java.math.BigDecimal;

public abstract class Food extends Product {

    public Food(String name, BigDecimal price) {
        super(name, price);
    }
}
