import java.util.ArrayList;
import java.util.List;

import entities.Account;
import items.*;

// Store class for inventory and sales of items.
public class Store {
    private List<Item> itemsForSale;

    public Store() {
        itemsForSale = new ArrayList<>();
        // Add items to the array
        itemsForSale.add(new Apartment());
        itemsForSale.add(new House());
        itemsForSale.add(new Mansion());
    }

    // Print out the items for sale (from the array).
    public void displayItems() { 
        System.out.println("Items for Sale:");
        for (Item item : itemsForSale) {
            System.out.println(item.getName() + " - $" + item.getCost());
        }
    }

    // Method for buying items
    public void buyItem(String itemName, Account account) {
        for (Item item : itemsForSale) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (account.getCashOnHand() >= item.getCost()) { // Maybe change to bank balance
                    account.withdraw(item.getCost()); // Deduct the cost from account.
                    item.applyEffect(account);        // Apply the item effect.
                    System.out.println("Purchased: " + item.getName());  // Purchase completed.
                    return;
                } else {
                    System.out.println("Insufficient funds to purchase: " + item.getName()); // Purchase failed because not enough money.
                    return;
                }
            }
        }
        System.out.println("Item not found: " + itemName); // Purchase failed because item was not found.
    }
}
