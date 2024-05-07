package game;

import javax.swing.*;
import java.awt.*;

import entities.Account;

public class Game implements Runnable {
    private GameWindow window;
    private GamePanel panel;
    private GameStateManager gsm;
    private Account account;
    private Thread gameThread;

    public Game(Account account) {
        this.account = account;

        // Main game panel using GameStateManager
        this.gsm = new GameStateManager();
        this.panel = new GamePanel(account, gsm); 
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