package common.models;

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
   // public Locale CountryOfOrigin; //todo: make enum? or class with countries codes
    //todo: for currency type Currency check it also Money and Currency API (JSR 354).
    //todo: add edible level between product and beverages/food
    private static AtomicInteger count = new AtomicInteger(0);

    public Product(String Name, BigDecimal Price, Date expirationDate){
        this.name = Name;
        this.price = Price;
        this.expirationDate = expirationDate;
        id = count.incrementAndGet();
    }
}
