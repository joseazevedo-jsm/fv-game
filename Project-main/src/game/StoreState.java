package game;

import entities.Account;
import javax.swing.*;
import java.awt.*;

// Credit: armanmo
// For the code for the panels

// Need to setup back button 
// Maybe take the structe of MainState code?
public class StoreState implements GameState {
    private JLabel bankBalanceLabel;
    private JLabel cashOnHandLabel;
    private GameStateManager gsm;

    public StoreState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void enter(GamePanel panel) {
        panel.removeAll(); // Clear the panel first
        // panel.add(createBalancePanel(panel.getBankAccount()), BorderLayout.NORTH);
        // panel.add(createActionPanel(panel.getBankAccount()), BorderLayout.CENTER);
        setupMainMenuUI(panel);
        panel.revalidate();
        panel.repaint();
    }

    private void setupMainMenuUI(GamePanel panel) {
        JButton backButton = new JButton("Back");


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Configure action listeners for each button
        backButton.addActionListener(e -> {
            System.out.println("Back button clicked");
            gsm.setState(new MainState(gsm), panel);
        });
    }

    private JPanel createInventoryPanel(Account bankAccount) {
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        // bankBalanceLabel = new JLabel("Balance: " + bankAccount.getBankBalance());
        // bankBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // bankBalanceLabel.setForeground(Color.BLACK);
        // bankBalanceLabel.setFont(new Font("Serif", Font.BOLD, 24));
        // balancePanel.add(bankBalanceLabel, BorderLayout.CENTER);

        // cashOnHandLabel = new JLabel("Cash on Hand: " + bankAccount.getCashOnHand());
        // cashOnHandLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // cashOnHandLabel.setForeground(Color.BLACK);
        // cashOnHandLabel.setFont(new Font("Serif", Font.BOLD, 24));
        // balancePanel.add(cashOnHandLabel, BorderLayout.SOUTH);

        return inventoryPanel;
    }

    private JPanel createActionPanel(Account bankAccount) {
        JPanel actionPanel = new JPanel(new GridBagLayout());
        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.gridwidth = GridBagConstraints.REMAINDER;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.insets = new Insets(5, 10, 5, 10);

        // JLabel messageLabel = new JLabel(" ");
        // messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // messageLabel.setForeground(Color.BLACK);
        // messageLabel.setFont(new Font("Serif", Font.BOLD, 28));
        // actionPanel.add(messageLabel, gbc);

        // JTextField amountField = new JTextField(20);
        // amountField.setInputVerifier(new IntegerInputVerifier());
        // amountField.setFont(new Font("Serif", Font.BOLD, 22));
        // actionPanel.add(amountField, gbc);

        // JButton depositButton = setupButton("Deposit", amountField, messageLabel, bankAccount, true);
        // JButton withdrawButton = setupButton("Withdraw", amountField, messageLabel, bankAccount, false);
        // actionPanel.add(depositButton, gbc);
        // actionPanel.add(withdrawButton, gbc);

        return actionPanel;
    }

    private JButton setupButton(String text, JTextField field, JLabel messageLabel, Account bankAccount, boolean isDeposit) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Serif", Font.BOLD, 28));
        button.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(field.getText());
                if (isDeposit) {
                    bankAccount.deposit(amount);
                } else {
                    bankAccount.withdraw(amount);
                }
                // updateBalanceLabels(bankAccount);
                messageLabel.setText(text + ": " + amount);
            } catch (IllegalArgumentException ex) {
                messageLabel.setText(ex.getMessage());
            }
            field.setText("");
        });
        return button;
    }

    @Override
    public void update() {
        // Update logic for the banking state
    }

    @Override
    public void draw(Graphics g) {
        // Draw on the panel if additional custom drawing is needed
    }

    @Override
    public void exit() {
        // Cleanup when exiting the banking state
    }

    public static class IntegerInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField textField = (JTextField) input;
            try {
                Integer.parseInt(textField.getText());
                return true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(input, "Please enter a valid number.");
                return false;
            }
        }
    }
}
