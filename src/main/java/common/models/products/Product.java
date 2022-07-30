package common.models.products;

import common.models.enums.Unit;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Product {
    public int id;
    public String name;
    public BigDecimal price;
    public Unit unit;
    public Date expirationDate;
    //todo: for currency type Currency check it also Money and Currency API (JSR 354).
    private static AtomicInteger count = new AtomicInteger(0);

    public Product(String name, BigDecimal price, Date expirationDate){
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;
        id = count.incrementAndGet();
    }
}
