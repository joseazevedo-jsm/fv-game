package game;

import java.awt.Graphics;

public interface GameState {
    void enter(GamePanel panel);
    void update();
    void draw(Graphics g);
    void exit();
}
