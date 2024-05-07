package items;

import entities.Account;

public class Mansion extends Item {
    public Mansion() {
        super("Mansion", 10000000); // The cost.
    }

    @Override
    public void applyEffect(Account account) {
        // The effect this should have.
    }
}