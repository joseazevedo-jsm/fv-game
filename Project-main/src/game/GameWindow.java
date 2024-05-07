package game;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow(String title, JPanel panel) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
    }
}