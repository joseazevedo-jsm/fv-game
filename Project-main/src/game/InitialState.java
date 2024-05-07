package game;

import javax.swing.*;

import entities.Account;
import entities.Player;

import java.awt.*;

public class InitialState implements GameState {
    private GameStateManager gsm;
    private Account account;
    private boolean difficultySelected = false;
    private Player player;

    public InitialState(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = Player.getInstance(); 
        this.account = Account.getInstance();
    }

    @Override
    public void enter(GamePanel panel) {
        panel.removeAll();
        panel.setLayout(new GridLayout(3, 1)); // Simple grid layout

        JButton btnDifficulty = new JButton("Select Difficulty");
        btnDifficulty.addActionListener(e -> {
            selectDifficulty();
        });

        JButton btnRules = new JButton("Game Rules");
        btnRules.addActionListener(e -> {
            showRules();
        });

        JButton btnStart = new JButton("Start Game");
        btnStart.addActionListener(e -> {
            if (difficultySelected) {
                gsm.setState(new NameEntryState(gsm), panel);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a difficulty first.");
            }
        });

        panel.add(btnDifficulty);
        panel.add(btnRules);
        panel.add(btnStart);

        panel.revalidate();
        panel.repaint();
    }

    private void selectDifficulty() {
        // Simple difficulty selection dialog
        Object[] options = { "Testing", "Cancel" };
        int choice = JOptionPane.showOptionDialog(null, "Select your difficulty:", "Difficulty Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // "Testing" difficulty selected
            // Adjusting the relevant things like: age, montlyearning etc.
            player.setAge(69);
            account.setMonthlyEarnings(1000);
            difficultySelected = true;
            JOptionPane.showMessageDialog(null, "Testing difficulty has been selected.");
        } else {
            // No difficulty selected or cancelled
            difficultySelected = false;
        }
    }

    private void showRules() {
        // Placeholder for showing rules using a simple dialog
        JOptionPane.showMessageDialog(null,
                "Here are the rules of the game:\n\n- Rule 1: You win by becoming rich\n- Rule 2: You lose by being bankrupt\n- Rule 3: You lose by dying\n- Rule 4: You will get a monthly income depending on your difficulty",
                "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void update() {
        // Update logic if necessary
    }

    @Override
    public void draw(Graphics g) {
        // Draw on the panel if needed
    }

    @Override
    public void exit() {
        // Cleanup when exiting this state
    }
}
