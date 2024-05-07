package items;

import entities.Account;
// Abstract class that should be inherited by all the diffrent items. 
public abstract class Item {
    protected String name;
    protected double cost;

    public Item(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    // Abstract method to apply the item's effect
    public abstract void applyEffect(Account account);
}
