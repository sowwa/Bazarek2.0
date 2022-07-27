package common.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

public class Product {
    public String Name;
    public BigDecimal Price;
    public Date ExpirationDate;
    public Locale CountryOfOrigin; //todo: make enum? or class with countries codes
    //todo: for currency type Currency check it also Money and Currency API (JSR 354).
    //todo: add edible level between product and beverages/food
    public Product(String Name, BigDecimal Price){
        this.Name = Name;
        this.Price = Price;
    }
}
