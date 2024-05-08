package game;
import entities.Account;
import entities.Inventory;
import entities.Player;

import javax.swing.*;
import java.awt.*;

/**
 * The main graphical interface component for the diffrent states. 
 */
public class GamePanel extends JPanel {
    private GameStateManager gsm;
    private Account bankAccount;
    private Player player;
    private Inventory inventory;

    public GamePanel(Inventory inventory, Player player, Account bankAccount, GameStateManager gsm) {
        this.player = player;
        this.bankAccount = bankAccount;
        this.inventory = inventory;
        this.gsm = gsm;
        setLayout(new BorderLayout());
    }

    public Account getBankAccount() {
        return bankAccount;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Method to return the gsm, making sure the diffrent states can make use of it.
     */ 
    public GameStateManager getGameStateManager() {
        return gsm;
    }
}
