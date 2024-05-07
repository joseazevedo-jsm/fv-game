package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class MainState implements GameState {
    private BufferedImage backgroundImage;
    private GameStateManager gsm;

    public MainState(GameStateManager gsm) {
        this.gsm = gsm;
        loadImage();
    }

    private void loadImage() {
        try {
            File imgFile = new File("FrontpageBank.png");
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
        GameStateManager gsm = panel.getGameStateManager();
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        if (backgroundImage != null) {
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setLayout(new BorderLayout());
            panel.add(backgroundLabel);
        } else {
            panel.add(new JLabel("Background image failed to load"), BorderLayout.CENTER);
        }
        setupMainMenuUI(panel);
        panel.revalidate();
        panel.repaint();
    }

    private void setupMainMenuUI(GamePanel panel) {
        JButton bankButton = new JButton("Bank Page");
        JButton inventoryButton = new JButton("Inventory Page");
        JButton investmentsButton = new JButton("Investments Page");
        JButton timeButton = new JButton("Time Page");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(bankButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(investmentsButton);
        buttonPanel.add(timeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Configure action listeners for each button
        bankButton.addActionListener(e -> {
            System.out.println("Bank Page button clicked");
            gsm.setState(new BankingState(gsm), panel);
        });

        inventoryButton.addActionListener(e -> {
            System.out.println("Inventory Page button clicked");
            gsm.setState(new InventoryState(gsm), panel);
        });

        investmentsButton.addActionListener(e -> {
            System.out.println("Store Page button clicked");
            gsm.setState(new InvestmentsState(gsm), panel);
        });

        timeButton.addActionListener(e -> {
            System.out.println("Time Page button clicked");
            gsm.setState(new TimeState(gsm), panel);
        });
    }

    @Override
    public void update() {
        // Update logic for the main menu
    }

    @Override
    public void draw(Graphics g) {
        // Optional drawing commands if additional graphics are needed
    }

    @Override
    public void exit() {
        // Cleanup UI components when exiting state
    }
}
