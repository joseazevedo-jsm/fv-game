package game;

import entities.Player;
import javax.swing.*;
import java.awt.*;

public class NameEntryState implements GameState {
    private GameStateManager gsm;

    public NameEntryState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void enter(GamePanel panel) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        // Using a BoxLayout to better control component sizes
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); 

        JLabel nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        centerPanel.add(nameLabel);

        JTextField nameField = new JTextField(20);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));  // Allow it to be wide but fixed height
        centerPanel.add(nameField);

        JButton submitButton = new JButton("Submit");
        submitButton.setMaximumSize(new Dimension(200, 30)); 
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);  
        centerPanel.add(submitButton);

        submitButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                Player.getInstance().setName(playerName);
                System.out.println("Player Name Set: " + playerName);
                gsm.setState(new MainState(gsm), panel);
            } else {
                JOptionPane.showMessageDialog(panel, "Please enter a name to continue.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel holderPanel = new JPanel(new GridBagLayout());
        holderPanel.add(centerPanel);

        panel.add(holderPanel, BorderLayout.CENTER);
        
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void update() {
        // Update logic if needed
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
