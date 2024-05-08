package game;

import entities.Account; // To add cash.
import entities.Player;

public class TimeController {
    private Player player;
    private Account account;  // Add reference to Account
    

    public TimeController(Player player, Account account) {
        this.player = Player.getInstance(); // Using the singleton instance of Player
        this.account = account; 
    }

    // Method to be called to increment the month
    public void incrementMonth() {
        player.increaseMonth();
        account.applyMonthlyEarnings();  // Apply monthly earnings to the account
        System.out.println("Time advances by one month.");

        if (player.getMonth() % 12 == 0) {  // One year has passed
            if (player.isAlive()) {
                if (player.getLifeExpectancy() - player.getAge() <= 1) {
                    System.out.println("The doctors say you only have 1 more year...");
                }
                player.incrementAge();
                System.out.println("Player Age: " + player.getAge());
            }
        }
    }

    // Returns the current month in String form.
    public String getCurrentMonth() {
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return months[player.getMonth() % 12];  // Cycle through months with modulus
    }

    //  public int getCurrentYear() {
    //     return player.getYear();
    // }
}