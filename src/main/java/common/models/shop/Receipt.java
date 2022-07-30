package common.models.shop;

import java.util.concurrent.atomic.AtomicInteger;

public class Receipt {
    public int id;
    private static AtomicInteger count = new AtomicInteger(0);

    public Receipt(){
        id = count.incrementAndGet();
    }

}
