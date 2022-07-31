package common.models.products;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Product {
    private final int id;
    private String name;
    private BigDecimal price;
    private LocalDate creationDate;
    private static final AtomicInteger count = new AtomicInteger(0);

    public Product(String name, BigDecimal price, LocalDate creationDate){
        this.name = name;
        this.price = price.round(new MathContext(3));
        this.creationDate = creationDate;
        id = count.incrementAndGet();
    }

    public LocalDate getCreationDate(){return this.creationDate;}
    public BigDecimal getPrice(){return this.price;}
    public String getName(){return this.name;}
    public int getId(){return this.id;}
}
