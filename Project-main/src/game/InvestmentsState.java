package game;

import entities.Account;
import entities.Inventory;
import items.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.util.function.Consumer;

public class InvestmentsState implements GameState, TimeObserver {
    private GameStateManager gsm;
    private GamePanel panel;
    private Map<String, Stock> stocks = new HashMap<>();
    //private Map<String, Property> properties = new HashMap<>();
    private Account account;
    private Inventory inventory;
    private JLabel bankBalanceLabel;
    private BufferedImage backgroundImage;

    public InvestmentsState(GameStateManager gsm) {
        this.gsm = gsm;
        this.account = Account.getInstance();
        this.inventory = Inventory.getInstance();
        initStocks();
        //initProperties();
        loadImage();
        setupBalanceLabel();
    }

    //ändra priserna då en månad har gått
    @Override
    public void onMonthChange() {
        for (Stock stock : stocks.values()) {
            stock.updatePrice();
        }
        //for (Property property : properties.values()) {
        //    property.updatePrice();
        //}
        if (panel != null) {
            panel.repaint();  // Ensure UI updates with new stock prices
        }
    }

    private void loadImage() {
        try {
            File imgFile = new File("Stocks.png");
            backgroundImage = ImageIO.read(imgFile);
            backgroundImage = scaleImage(backgroundImage);
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    //använde detta så att bilden täcker hela skärmen både i fullscreen och när det inte är fullscreen
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
    
    //De olika aktierna och deras knappar
    private void initStocks() {
        Stock investorStock = new Stock("Investor", 100, 0.035);
        stocks.put("Investor", investorStock);
        JButton investorButton = new JButton();
        investorStock.setButton(investorButton);
        
        Stock microsoftStock = new Stock("Microsoft", 100, 0.075);
        stocks.put("Microsoft", microsoftStock);
        JButton microsoftButton = new JButton();
        microsoftStock.setButton(microsoftButton);
    
        Stock teslaStock = new Stock("Tesla", 100, 0.10);
        stocks.put("Tesla", teslaStock);
        JButton teslaButton = new JButton();
        teslaStock.setButton(teslaButton);

        Stock viaplayStock = new Stock("Viaplay", 100, 0.175);
        stocks.put("Viaplay", viaplayStock);
        JButton viaplayButton = new JButton();
        viaplayStock.setButton(viaplayButton);
    
        Stock amcStock = new Stock("AMC", 100, 0.4);
        stocks.put("AMC", amcStock);
        JButton amcButton = new JButton();
        amcStock.setButton(amcButton);

        Stock sasStock = new Stock("SAS", 100, 0.25);
        stocks.put("SAS", sasStock);
        JButton sasButton = new JButton();
        sasStock.setButton(sasButton);
    }
    
    // Shouldnt be here, should be in a serpate store (in my opinion) - fmalmbe
    //private void initProperties() {
    //    Property apartmentProperty = new Property("Apartment", 200000, 0.05);
    //    properties.put("Apartment", apartmentProperty);
    //    JButton apartmentButton = new JButton();
    //    apartmentProperty.setButton(apartmentButton);
    //
    //    Property commercialProperty = new Property("Commercial Space", 500000, 0.10);
    //    properties.put("Commercial Space", commercialProperty);
    //    JButton commercialButton = new JButton();
    //    commercialProperty.setButton(commercialButton);
    
    //    Property landProperty = new Property("Land", 150000, 0.20);
    //    properties.put("Land", landProperty);
    //    JButton landButton = new JButton();
    //    landProperty.setButton(landButton);
    //}

    //Har användarens balance längst upp 
    private void setupBalanceLabel() {
        bankBalanceLabel = new JLabel("Balance: $" + String.format("%.2f", account.getBankBalance()));
        bankBalanceLabel.setFont(new Font("Arial", Font.BOLD, 28));
        bankBalanceLabel.setForeground(Color.WHITE);
        bankBalanceLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public void enter(GamePanel panel) {
        this.panel = panel;
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setLayout(new BorderLayout());
        setupContentPanels(backgroundLabel);

        panel.add(backgroundLabel);
        panel.revalidate();
        panel.repaint();
    }

    private void setupContentPanels(JLabel backgroundLabel) {
        JPanel mainStockPanel = new JPanel(new BorderLayout());
        mainStockPanel.setOpaque(false); //Gör den transparent 
        JLabel stocksLabel = createSectionLabel("Stocks:");
        mainStockPanel.add(stocksLabel, BorderLayout.NORTH);
    
        //har 2 kolonner eftersom vi har en vänster och höger sida
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        contentPanel.setOpaque(false);
    
        JPanel leftStockPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        leftStockPanel.setOpaque(false); //Gör vänstra sidan transparent
        JPanel rightStockPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        rightStockPanel.setOpaque(false); //Gör högra sidan transparent
    
        setupStockPanel(leftStockPanel, rightStockPanel); //lägg till aktierna för varje sida
    
        contentPanel.add(leftStockPanel);
        contentPanel.add(rightStockPanel);
    
        mainStockPanel.add(contentPanel, BorderLayout.CENTER);
    
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        balancePanel.setOpaque(false);
        balancePanel.add(bankBalanceLabel);
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> gsm.setState(new MainState(gsm), panel));
        backButton.setOpaque(false);
    
        backgroundLabel.add(balancePanel, BorderLayout.NORTH);
        backgroundLabel.add(mainStockPanel, BorderLayout.CENTER);
        backgroundLabel.add(backButton, BorderLayout.SOUTH);
    }
    
    private void setupStockPanel(JPanel leftStockPanel, JPanel rightStockPanel) {
        Font buttonFont = new Font("Arial", Font.BOLD, 30);
        int count = 0; 
        for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
            JButton button = new JButton("Buy " + entry.getKey() + " - Current Value: $" + entry.getValue().getCurrentValue());
            button.setFont(buttonFont);
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(250, 50));
            button.setOpaque(false); // Ensure buttons are also transparent
            button.setContentAreaFilled(false); // Do not fill the content area
            button.setBorderPainted(false); // Optionally remove button border if desired
            button.addActionListener(e -> buyStock(entry.getValue()));
            if (count++ < 3) { //Lägger först 3 aktier för vänstra sidan
                leftStockPanel.add(button);
            } else { // Resterande 3 aktier går till högra sidan
                rightStockPanel.add(button);
            }
        }
    }
    

    //private void setupPropertyPanel(JPanel parentPanel) {
    //    JPanel propertyPanel = new JPanel(new GridLayout(4, 1, 0, 5));
    //    propertyPanel.setOpaque(false);
    //    JLabel propertiesLabel = createSectionLabel("Properties:");
    //    propertyPanel.add(propertiesLabel);

    //    properties.forEach((name, property) -> {
    //        JButton button = new JButton();
    //        button.setFont(new Font("Arial", Font.BOLD, 20));
    //        button.setPreferredSize(new Dimension(250, 50));
    //        button.addActionListener(e -> buyProperty(property));
    //        property.setButton(button);  // Link the property with its button
    //        propertyPanel.add(button);
    //    });

    //    parentPanel.add(propertyPanel);
    //}

    //skapar "Stocks" rubriken över aktierna
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 34));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private void buyStock(Stock stock) {
        String quantityString = JOptionPane.showInputDialog(panel, "Enter quantity to buy for " + stock.getName() + ":", "Stock Purchase", JOptionPane.PLAIN_MESSAGE);
        handlePurchase(quantityString, stock.getCurrentValue(), quantity -> stock.purchaseStock(quantity));
        inventory.addInvestment(stock);
        System.out.println("Stock purchased: " + inventory.getOwnedInvestments());
    }

    //private void buyProperty(Property property) {
    //    String quantityString = JOptionPane.showInputDialog(panel, "Enter quantity to buy for " + property.getName() + ":", "Property Purchase", JOptionPane.PLAIN_MESSAGE);
    //    handlePurchase(quantityString, property.getCurrentValue(), quantity -> property.purchaseProperty(quantity));
    //}

    private void handlePurchase(String quantityString, double price, Consumer<Integer> purchaseMethod) {
        if (quantityString != null) {
            try {
                int quantity = Integer.parseInt(quantityString);
                if (quantity > 0) {
                    double totalCost = quantity * price;
                    if (this.account.getBankBalance() >= totalCost) {
                        this.account.subtractFromBalance(totalCost);
                        purchaseMethod.accept(quantity);
                        JOptionPane.showMessageDialog(panel, "Successfully purchased " + quantity + " units.");
                        updateBalanceLabel();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Insufficient funds to purchase " + quantity + " units.", "Transaction Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please enter a positive number.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateBalanceLabel() {
        bankBalanceLabel.setText("Balance: $" + String.format("%.2f", account.getBankBalance()));
    }

    @Override
    public void update() {
        // Optional: Implement if you have dynamic elements to update
    }

    @Override
    public void draw(Graphics g) {
        // Draw if needed, for more complex visuals
    }

    @Override
    public void exit() {
        // Cleanup if needed
    }

    // class Property {
    //     private String name;
    //     private double baseValue;
    //     private double currentValue;
    //     private double fluctuation; // Percentage change
    //     private boolean priceUpdated = false;
    //     private int quantityOwned; // New field to track quantity owned
    //     private JButton button; // Button linked to this property

    //     public Property(String name, double baseValue, double fluctuation) {
    //         this.name = name;
    //         this.baseValue = baseValue;
    //         this.currentValue = baseValue;
    //         this.fluctuation = fluctuation;
    //         this.quantityOwned = 0; // Initialize with zero owned
    //     }

    //     public String getName() {
    //         return name;
    //     }

    //     public double getCurrentValue() {
    //         return currentValue;
    //     }

    //     public int getQuantityOwned() {
    //         return quantityOwned;
    //     }

    //     public void setButton(JButton button) {
    //         this.button = button;
    //         updatePrice();  // Initial setting to reflect current value
    //     }

    //     public void updatePrice() {
    //         double changeFactor = (Math.random() * (fluctuation * 2)) - fluctuation;
    //         currentValue = Math.round(currentValue * (1 + changeFactor));
    //         button.setText("Buy " + name + " - Value: $" + currentValue);
    //     }

    //     public void purchaseProperty(int quantity) {
    //         quantityOwned += quantity; // Increment by the purchased quantity
    //     }
    // }
}
