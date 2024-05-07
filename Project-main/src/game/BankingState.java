package game;

import entities.Account;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

// Credit: armanmo
// For the code for the panels

// Need to setup back button 
// Maybe take the structe of MainState code?
public class BankingState implements GameState {
    private JLabel bankBalanceLabel;
    private JLabel cashOnHandLabel;
    private GameStateManager gsm;
    private BufferedImage backgroundImage;

    public BankingState(GameStateManager gsm) {
        this.gsm = gsm;
        loadImage();
    }

    private void loadImage() {
        try {
            File imgFile = new File("BildBank.png");
            backgroundImage = ImageIO.read(imgFile);
            backgroundImage = scaleImage(backgroundImage);
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    private BufferedImage scaleImage(BufferedImage sourceImage) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        Image scaledImage = sourceImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return newImage;
    }

    @Override
    public void enter(GamePanel panel) {
        panel.removeAll(); // Clear the panel first

        // Setup the main UI elements such as balance and action panels
        JPanel balancePanel = createBalancePanel(panel.getBankAccount());
        JPanel actionPanel = createActionPanel(panel.getBankAccount());

        // Set the background if the image was loaded successfully
        if (backgroundImage != null) {
            panel.setLayout(new BorderLayout()); // Set layout

            // Create a background label and add it to the panel
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setLayout(new BorderLayout());
            panel.add(backgroundLabel);

            // Add balance and action panels over the background label
            backgroundLabel.add(balancePanel, BorderLayout.NORTH);
            backgroundLabel.add(actionPanel, BorderLayout.CENTER);
        } else {
            // Handle the failed background image loading appropriately
            panel.setLayout(new BorderLayout());
            panel.add(new JLabel("Background image failed to load"), BorderLayout.CENTER);
            panel.add(balancePanel, BorderLayout.NORTH);
            panel.add(actionPanel, BorderLayout.CENTER);
        }

        // Setup the back button and its panel
        setupBackButtonUI(panel);

        panel.revalidate();
        panel.repaint();
    }

    private void setupBackButtonUI(GamePanel panel) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            System.out.println("Back button clicked");
            gsm.setState(new MainState(gsm), panel);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        // Add the button panel to the SOUTH region of the background or the panel
        if (backgroundImage != null) {
            // Assuming backgroundLabel was added to panel and holds the current content
            ((JLabel) panel.getComponent(0)).add(buttonPanel, BorderLayout.SOUTH);
        } else {
            panel.add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    private JPanel createBalancePanel(Account bankAccount) {
        JPanel balancePanel = new JPanel(new BorderLayout());
        bankBalanceLabel = new JLabel("Balance: " + bankAccount.getBankBalance());
        bankBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bankBalanceLabel.setForeground(Color.BLACK);
        bankBalanceLabel.setFont(new Font("Serif", Font.BOLD, 24));
        balancePanel.add(bankBalanceLabel, BorderLayout.CENTER);

        cashOnHandLabel = new JLabel("Cash on Hand: " + bankAccount.getCashOnHand());
        cashOnHandLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cashOnHandLabel.setForeground(Color.BLACK);
        cashOnHandLabel.setFont(new Font("Serif", Font.BOLD, 24));
        balancePanel.add(cashOnHandLabel, BorderLayout.SOUTH);

        balancePanel.setOpaque(false);
        return balancePanel;
    }

    private JPanel createActionPanel(Account bankAccount) {
        JPanel actionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel messageLabel = new JLabel(" ");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setFont(new Font("Serif", Font.BOLD, 28));
        actionPanel.add(messageLabel, gbc);

        JTextField amountField = new JTextField(20);
        amountField.setInputVerifier(new IntegerInputVerifier());
        amountField.setFont(new Font("Serif", Font.BOLD, 22));
        actionPanel.add(amountField, gbc);

        JButton depositButton = setupButton("Deposit", amountField, messageLabel, bankAccount, true);
        JButton withdrawButton = setupButton("Withdraw", amountField, messageLabel, bankAccount, false);
        actionPanel.add(depositButton, gbc);
        actionPanel.add(withdrawButton, gbc);

        actionPanel.setOpaque(false);
        return actionPanel;
    }

    private JButton setupButton(String text, JTextField field, JLabel messageLabel, Account bankAccount,
            boolean isDeposit) {
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
                updateBalanceLabels(bankAccount);
                messageLabel.setText(text + ": " + amount);
            } catch (IllegalArgumentException ex) {
                messageLabel.setText(ex.getMessage());
            }
            field.setText("");
        });
        return button;
    }

    private void updateBalanceLabels(Account bankAccount) {
        bankBalanceLabel.setText("Balance: " + bankAccount.getBankBalance());
        cashOnHandLabel.setText("Cash on Hand: " + bankAccount.getCashOnHand());
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
