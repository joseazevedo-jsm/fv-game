package entities;
import java.util.ArrayList;
import java.util.List;

import items.Apartment;
import items.House;
import items.Item;
import items.Mansion;
import items.Stock;

//har koll på aktierna man äger bland annat
public class Inventory {
    private static Inventory instance;
    private List<Item> ownedItems;
    private List<Stock> ownedInvestments;

    // Constructor

    public Inventory() {
        ownedItems = new ArrayList<>();
        ownedInvestments = new ArrayList<>();

        // Add some default items and investments to the inventory.
        ownedItems.add(new Apartment());
        ownedItems.add(new House());
        // ownedItems.add(new Mansion());
        // ownedInvestments.add(new Stock());
        // ownedInvestments.add(new Stock());
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
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
    public void addInvestment(Stock stock) {
        ownedInvestments.add(stock);
    }

    // sellInvestment removes an investment from the inventory.
    public void sellInvestment(Stock stock) {
        ownedInvestments.remove(stock);
    }

    // getItems returns the list of items in the inventory.
    public List<Item> getOwnedItems() {
        return ownedItems;
    }

    // getInvestments returns the list of investments in the inventory.
    public List<Stock> getOwnedInvestments() {
        return ownedInvestments;
    }
}