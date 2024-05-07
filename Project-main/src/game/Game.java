package game;

import javax.swing.*;
import java.awt.*;

import entities.Account;
import entities.Inventory;
import entities.Investment;
import entities.Player;

public class Game implements Runnable {
    private GameWindow window;
    private GamePanel panel;
    private GameStateManager gsm;
    private Thread gameThread;

    public Game(Inventory inventory, Investment investment, Player player, Account account) {
        // Main game panel using GameStateManager
        this.gsm = new GameStateManager();
        this.panel = new GamePanel(inventory, investment, player, account, gsm); 
        this.window = new GameWindow("My Game", panel);

        InvestmentsState investmentsState = new InvestmentsState(gsm);
        gsm.addTimeObserver(investmentsState);  // Register as observer

        // Initialize the main state with the background image and buttons
        gsm.setState(new InitialState(this.gsm), panel);  
        window.setVisible(true);  
    }

    public void start() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    @Override
    public void run() {
        while (true) {
            update();
            render();
            try {
                Thread.sleep(16);  // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        gsm.update();
    }

    private void render() {
        panel.repaint();
    }
}
