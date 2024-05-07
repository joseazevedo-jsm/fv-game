package entities;
import java.util.ArrayList;
import java.util.List;

import items.Apartment;
import items.House;
import items.Item;
import items.Mansion;

//har koll på aktierna man äger bland annat
public class Inventory {
    private List<Item> ownedItems;
    private List<Investment> ownedInvestments;

    // Constructor

    public Inventory() {
        ownedItems = new ArrayList<>();
        ownedInvestments = new ArrayList<>();

        // Add some default items and investments to the inventory.
        ownedItems.add(new Apartment());
        ownedItems.add(new House());
        ownedItems.add(new Mansion());
        ownedInvestments.add(new Investment("Amazon", 200.0, 0.15));
        ownedInvestments.add(new Investment("Apple", 100.0, 0.05));
    }

    
    // Methods:

     // addItem adds an item to the inventory.
     public void addItem(Item item) {
        ownedItems.add(item);
    }

    // sellItem removes an item from the inventory.
    public void sellItem(Item item) {
        ownedItems.remove(item);
    }

    // addInvestment adds an investment to the inventory.
    public void addInvestment(Investment investment) {
        ownedInvestments.add(investment);
    }

    // sellInvestment removes an investment from the inventory.
    public void sellInvestment(Investment investment) {
        ownedInvestments.remove(investment);
    }

    // getItems returns the list of items in the inventory.
    public List<Item> getOwnedItems() {
        return ownedItems;
    }

    // getInvestments returns the list of investments in the inventory.
    public List<Investment> getOwnedInvestments() {
        return ownedInvestments;
    }
}