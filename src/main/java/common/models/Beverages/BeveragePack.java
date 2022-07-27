package common.models.Beverages;

public class BeveragePack<T extends Beverage> {
    public int Qty;
    public Iterable<T> Beverage;
}

