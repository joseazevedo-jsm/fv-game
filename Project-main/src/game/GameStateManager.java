package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
    private GameState currentState;
    private List<TimeObserver> timeObservers = new ArrayList<>(); // List to hold all time observers

    public void setState(GameState newState, GamePanel panel) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        if (newState != null) {
            newState.enter(panel);
        }
    }

    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    public void draw(Graphics g) {
        if (currentState != null) {
            currentState.draw(g);
        }
    }

    // Add a time observer to the list
    public void addTimeObserver(TimeObserver observer) {
        timeObservers.add(observer);
    }

    // Notify all registered time observers that the month has changed
    public void notifyTimeObservers() {
        for (TimeObserver observer : timeObservers) {
            observer.onMonthChange();
        }
    }
}
