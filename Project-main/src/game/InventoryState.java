package game;

import entities.Account;
import javax.swing.*;
import java.awt.*;

// Credit: armanmo
// For the code for the panels

// Need to setup back button 
// Maybe take the structe of MainState code?
public class InventoryState implements GameState {
    private JLabel bankBalanceLabel;
    private JLabel cashOnHandLabel;
    private GameStateManager gsm;
    private Player player;
    private Account account;
    private Inventory inventory;
    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel moneyLabel;
    private JList<String> ownedItemsList;
    private JList<String> investmentsList;

    public InventoryState(GameStateManager gsm) {
        this.gsm = gsm;
         super(gsm);
        // Initialize player, account, and inventory objects
        player = Player.getInstance();
        account = player.getAccount();
        inventory = player.getInventory();
        // Create UI elements
        nameLabel = new JLabel("Name: " + player.getName());
        ageLabel = new JLabel("Age: " + player.getAge());
        monthLabel = new JLabel("Month: " + player.getMonth());
        yearLabel = new JLabel("Year: " + player.getYear());
        moneyLabel = new JLabel("Money: " + account.getBalance());
        ownedItemsList = new JList<>(inventory.getOwnedItems().toArray());
        investmentsList = new JList<>(inventory.getInvestments().toArray());
        // Set up the layout
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(nameLabel);
        panel.add(ageLabel);
        panel.add(monthLabel);
        panel.add(yearLabel);
        panel.add(moneyLabel);
        panel.add(new JLabel("Owned Items:"));
        panel.add(ownedItemsList);
        panel.add(new JLabel("Investments:"));
        panel.add(investmentsList);
        add(panel);
    }

    @Override
    public void enter(GamePanel panel) {
        panel.removeAll(); // Clear the panel first
        panel.add(this);
        // panel.add(createBalancePanel(panel.getBankAccount()), BorderLayout.NORTH);
        // panel.add(createActionPanel(panel.getBankAccount()), BorderLayout.CENTER);
        setupMainMenuUI(panel);
        panel.revalidate();
        panel.repaint();
    }

    // View the montly earnings here, how much you make!!! (see the account.java to see the neccessary method)
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
        player = Player.getInstance();
        account = player.getAccount();
        inventory = player.getInventory();
        // Update the UI elements with the new information
        nameLabel.setText("Name: " + player.getName());
        ageLabel.setText("Age: " + player.getAge());
        monthLabel.setText("Month: " + player.getMonth());
        yearLabel.setText("Year: " + player.getYear());
        moneyLabel.setText("Money: " + account.getBalance());
        ownedItemsList.setListData(inventory.getOwnedItems().toArray());
        investmentsList.setListData(inventory.getInvestments().toArray());
    }

    @Override
    public void draw(Graphics g) {
        // Draw on the panel if additional custom drawing is needed
         g.drawImage(backgroundImage, 0, 0, null);
        // Draw the panel with the player's information
        panel.paint(g);
    }

    @Override
    public void exit() {
        // Cleanup when exiting the banking state
        this.getParent().remove(this);
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
