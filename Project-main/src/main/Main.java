package main;

import javax.swing.SwingUtilities;
import game.Game;  // Assuming Game manages the overall game loop and states
import entities.Account;  // Your Account class
import entities.Inventory;
import entities.Investment;
import entities.Player;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Account account = Account.getInstance();
            Player player = Player.getInstance();
            Investment investment = new Investment();
            Inventory inventory = new Inventory();

            Game game = new Game(inventory, investment, player, account);  // Assuming Game is prepared to handle game logic
            game.start();  // Start the game logic loop
        });
    }
}
