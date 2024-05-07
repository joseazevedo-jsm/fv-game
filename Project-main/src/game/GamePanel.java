package game;
import entities.Account;
import javax.swing.*;
import java.awt.*;

/**
 * The main graphical interface component for the diffrent states. 
 */
public class GamePanel extends JPanel {
    private GameStateManager gsm;
    private Account bankAccount;

    public GamePanel(Account bankAccount, GameStateManager gsm) {
        this.bankAccount = bankAccount;
        this.gsm = gsm;
        setLayout(new BorderLayout());
    }

    public Account getBankAccount() {
        return bankAccount;
    }

    /**
     * Method to return the gsm, making sure the diffrent states can make use of it.
     */ 
    public GameStateManager getGameStateManager() {
        return gsm;
    }
}
