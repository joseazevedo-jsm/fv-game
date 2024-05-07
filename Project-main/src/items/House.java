package items;

import entities.Account;
import entities.Player;

public class House extends Item {
    private Account account;
    public House() {
        super("House", 1000000); // The cost.
        this.account = Account.getInstance(); 
    }

    @Override
    public void applyEffect(Account account) {
        // The effect this should have.
    }
}