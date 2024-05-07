package entities;

// Created by: armanmo
// Contributers: fmalmbe

// Makes use of class-based singleton
// https://www.baeldung.com/java-singleton 

public class Account {
    // private String accountNumber;
    // private String accountHolderName;
    private static Account instance;
    private double bankBalance; // Money in the bank
    private double cashOnHand; // Money carried by the person
    private double monthlyEarnings;


    public Account(double bankBalance, double cashOnHand) {
        // this.accountNumber = accountNumber;
        // this.accountHolderName = accoutHolderName;
        this.bankBalance = bankBalance;
        this.cashOnHand = cashOnHand;
        this.monthlyEarnings = 0;
    }

    // Static method to get instance
    public static Account getInstance() {
        if (instance == null) {
            instance = new Account(1000.0, 500.0);
        }
        return instance;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public double getCashOnHand() {
        return cashOnHand;
    }

    public double getMonthlyEarnings() {
        return monthlyEarnings;
    }

    public void setMonthlyEarnings(double earnings) {
        this.monthlyEarnings = earnings;
    }

    public void increaseMonthlyEarnings(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive.");
        }
        this.monthlyEarnings += amount;
    }

    public void decreaseMonthlyEarnings(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive.");
        }
        // if (this.monthlyEarnings < amount) {
        //     throw new IllegalArgumentException("Cannot decrease monthly earnings below zero.");
        // }
        this.monthlyEarnings -= amount;
    }

    public void applyMonthlyEarnings() {
        bankBalance += monthlyEarnings;
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        if (cashOnHand < amount) {
            throw new IllegalArgumentException("Cannot diposit more than " + cashOnHand + " to the bank.");
        }
        cashOnHand -= amount;
        bankBalance += amount;
    }
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > bankBalance) {
            throw new IllegalArgumentException("Cannot withdraw more than " + bankBalance + " from bank.");
        }
        bankBalance -= amount;
        cashOnHand += amount;
    }
    public void subtractFromBalance(double amount) {
        bankBalance -= amount;
    }
}