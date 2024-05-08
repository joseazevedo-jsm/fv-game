package main;

import javax.swing.SwingUtilities;
import game.Game;  // Assuming Game manages the overall game loop and states
import entities.Account;  // Your Account class
import entities.Inventory;
import entities.Player;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize game entities
            Account account = Account.getInstance();
            Player player = Player.getInstance();
            Inventory inventory = Inventory.getInstance();

            Game game = new Game(inventory, player, account);  // Assuming Game is prepared to handle game logic
            game.start();  // Start the game logic loop
        });
    }
}
