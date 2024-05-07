package entities;

import java.util.ArrayList;
import java.util.List;

// This is an example of a Java class that represents an investment.
// An investment has a name, a value, and a growth rate.

public class Investment {
    private String name;
    private double value;
    private double growthRate;

    // Constructor
    public Investment() {
    }

    public Investment(String name, double value, double growthRate) {
        this.name = name;
        this.value = value;
        this.growthRate = growthRate;
    }

    // Methods:

    // getName
    public String getName() {
        return name;
    }

    // getValue
    public double getValue() {
        return value;
    }

    // getGrowthRate
    public double getGrowthRate() {
        return growthRate;
    }

    // setValue
    public void setValue(double value) {
        this.value = value;
    }

    // setGrowthRate
    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    // toString
    @Override
    public String toString() {
        return "Investment [name=" + name + ", value=" + value + ", growthRate=" + growthRate + "]";
    }

    // Example usage:
    public static void main(String[] args) {
        // Create a list of investments
        List<Investment> investments = new ArrayList<>();

        // Add some investments to the list
        investments.add(new Investment("Apple", 100.0, 0.05));
        investments.add(new Investment("Google", 150.0, 0.10));
        investments.add(new Investment("Amazon", 200.0, 0.15));

        // Print the list of investments
        for (Investment investment : investments) {
            System.out.println(investment);
        }
    }
}
