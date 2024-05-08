package items;

import javax.swing.JButton;

public class Stock {
    private String name;
    private double baseValue;
    private double currentValue;
    private double fluctuation; // Percentage change
    private boolean priceUpdated = false;
    private int quantityOwned; // New field to track quantity owned
    private JButton button; // Button linked to this stock

    public Stock() {
    }

    public Stock(String name, double baseValue, double fluctuation) {
        this.name = name;
        this.baseValue = baseValue;
        this.currentValue = baseValue;
        this.fluctuation = fluctuation;
        this.quantityOwned = 0; // Initialize with zero owned
    }

    public String getName() {
        return name;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public int getQuantityOwned() {
        return quantityOwned;
    }

    public void setButton(JButton button) {
        this.button = button;
        updatePrice();  // Initial setting to reflect current value
    }

    public void updatePrice() {
        double changeFactor = (Math.random() * (fluctuation * 2)) - fluctuation;
        currentValue = Math.round(currentValue * (1 + changeFactor));
        button.setText("Buy " + name + " - Current Value: $" + currentValue); // Complain about this, because "this.button" is null
    }

    public void purchaseStock(int quantity) {
        quantityOwned += quantity; // Increment by the purchased quantity
    }
}
