package items;

import entities.Account;

public class Apartment extends Item {
    public Apartment() {
        super("Apartment", 100000); // The cost.
    }

    @Override
    public void applyEffect(Account account) {
        Account.getInstance().decreaseMonthlyEarnings(200);
    }
}