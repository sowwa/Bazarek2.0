package common.models.shop;

public class OrderProductDiscount {
    private String discountName;
    private boolean applied;//todo: delete I think, so this whole class unnesessary? unless a list of discounts?

    public OrderProductDiscount(String discountName, boolean applied){
        this.discountName = discountName;
        this.applied = applied;
    }
    public String getName(){return discountName;}
}
