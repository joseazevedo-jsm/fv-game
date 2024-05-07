package main;

import javax.swing.SwingUtilities;
import game.Game;  // Assuming Game manages the overall game loop and states
import entities.Account;  // Your Account class

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Account account = Account.getInstance();
            Game game = new Game(account);  // Assuming Game is prepared to handle game logic
            game.start();  // Start the game logic loop
        });
    }
}
