package game;

import entities.Account;
import entities.Inventory;
import entities.Player;
import items.Item;
import items.Stock;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Credit: armanmo
// For the code for the panels

// Need to setup back button 
// Maybe take the structe of MainState code?
public class InventoryState implements GameState {
    private GameStateManager gsm;
    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel moneyLabel;
    private JLabel investmentLaLabel;
    private JLabel itemLsabel;
    private BufferedImage backgroundImage;


    public InventoryState(GameStateManager gsm) {
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

    @Override
    public void enter(GamePanel panel) {
        panel.removeAll(); // Clear the panel first

        // Setup the main UI elements such as balance and action panels
        System.out.println("Owned investments: " + panel.getInventory().getOwnedInvestments() + "Owned items: " + panel.getInventory().getOwnedItems());
        System.out.println("Bank account: " + panel.getBankAccount());
        System.out.println("Player: " + panel.getPlayer());

        JPanel allPanels = createInventoryPanel(panel.getInventory(),panel.getBankAccount(),panel.getPlayer());
        
        // Set the background if the image was loaded successfully
        if (backgroundImage != null) {
        panel.setLayout(new BorderLayout()); // Set layout

        // Create a background label and add it to the panel
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setLayout(new BorderLayout());
        panel.add(backgroundLabel);

        // Add balance and action panels over the background label
        backgroundLabel.add(allPanels, BorderLayout.CENTER);
        } else {
            // Handle the failed background image loading appropriately
            panel.setLayout(new BorderLayout());
            panel.add(new JLabel("Background image failed to load"), BorderLayout.CENTER);
            panel.add(allPanels, BorderLayout.CENTER);
        }

        // Setup the back button and its panel
        setupBackButtonUI(panel);

        panel.revalidate();
        panel.repaint();
    }

    // View the montly earnings here, how much you make!!! (see the account.java to see the neccessary method)
    private JPanel createInventoryPanel(Inventory inventory,Account bankAccount, Player player ) {
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        LocalDate currentDate = LocalDate.now();

        int currentYear = currentDate.getYear();

        // Convert the current month to a string
        String currentMonth = getCurrentMonth(player);


         // Create UI elements
         nameLabel = new JLabel("Name: " + player.getName());
         ageLabel = new JLabel("Age: " + player.getAge());
         monthLabel = new JLabel("Month: " + currentMonth.toUpperCase());
         yearLabel = new JLabel("Year: " + String.valueOf(currentYear));
         moneyLabel = new JLabel("Money: " + bankAccount.getBankBalance());
         itemLsabel = new JLabel("Houses: ");
         investmentLaLabel = new JLabel("Investments: ");


        // Create a DefaultListModel to hold the item names
        DefaultListModel<String> listModel = new DefaultListModel<>();

        // Add the items from the getOwnedItems list to the DefaultListModel
        for (  Item item : inventory.getOwnedItems()) {
        listModel.addElement(item.getName());
        }

        // Create a JList object and set its model to the DefaultListModel
        JList<String> ownedItemsList = new JList<>(listModel);

        DefaultListModel<String> listModel2 = new DefaultListModel<>();

        // Add the items from the getOwnedItems list to the DefaultListModel
        for (Stock investment : inventory.getOwnedInvestments()) {
        listModel2.addElement(investment.getName());
        }

        // Create a JList object and set its model to the DefaultListModel
        JList<String> investmentsList = new JList<>(listModel2);


         JPanel playerInfoPanel = new JPanel();
         playerInfoPanel.add(nameLabel);
         playerInfoPanel.add(ageLabel);
         playerInfoPanel.add(monthLabel);
         playerInfoPanel.add(yearLabel);
         playerInfoPanel.add(moneyLabel);
         playerInfoPanel.add(itemLsabel);
         playerInfoPanel.add(ownedItemsList);
         playerInfoPanel.add(investmentLaLabel);
         playerInfoPanel.add(investmentsList);
         

    
        inventoryPanel.add(playerInfoPanel);

        return inventoryPanel;
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

    public String getCurrentMonth(Player player) {
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return months[player.getMonth() % 12];  // Cycle through months with modulus
    }

    @Override
    public void update() {
        // Update logic for the banking state
    }

    @Override
    public void draw(Graphics g) {
        // Draw on the panel if additional custom drawing is needed
        // Draw the panel with the player's information
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
