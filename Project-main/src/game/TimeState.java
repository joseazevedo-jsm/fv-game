package game;

import javax.swing.*;

import entities.Account;
import entities.Player;
import java.awt.*;
import java.util.List; // Import the List interface

public class TimeState implements GameState {
    private GameStateManager gsm;
    private Player player;
    private Account account;
    private TimeController timeController;  // Time controller instance
    private JLabel feedbackLabel; // To get feedback
    private JProgressBar monthProgress;
    private JLabel ageLabel;
    private JLabel currentMonthLabel;
    private JLabel lastMonthEarningsLabel;

    public TimeState(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = Player.getInstance(); 
        this.account = Account.getInstance();
        this.timeController = new TimeController(player, account);  
    }

    @Override
    public void enter(GamePanel panel) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        setupTimeAdvanceUI(panel);
        panel.revalidate();
        panel.repaint();
    }

    private void setupTimeAdvanceUI(GamePanel panel) {
        // Button to advance time
        JButton advanceTimeButton = new JButton("Advance Time by One Month");
        JButton backButton = new JButton("Back"); // Back button to return to the main menu

        // Initialize labels for additional information
        feedbackLabel = new JLabel("Click the button to advance time.", SwingConstants.CENTER);
        feedbackLabel.setForeground(Color.BLUE);
        feedbackLabel.setFont(new Font("Serif", Font.BOLD, 16));

        ageLabel = new JLabel("Age: " + player.getAge());
        currentMonthLabel = new JLabel("Current Month: " + timeController.getCurrentMonth());
        lastMonthEarningsLabel = new JLabel("Last Month's Earnings: $" + account.getMonthlyEarnings());

        // Progress bar to simulate time passing
        monthProgress = new JProgressBar();
        monthProgress.setStringPainted(false);
        monthProgress.setMaximum(100);

        // Center panel to hold the buttons, labels, and progress bar
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        centerPanel.add(feedbackLabel, gbc);
        centerPanel.add(ageLabel, gbc);
        centerPanel.add(currentMonthLabel, gbc);
        centerPanel.add(lastMonthEarningsLabel, gbc);
        centerPanel.add(monthProgress, gbc);
        centerPanel.add(advanceTimeButton, gbc);
        centerPanel.add(backButton, gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Action listeners
        advanceTimeButton.addActionListener(e -> {
            simulateMonthProgress();
        });

        backButton.addActionListener(e -> {
            System.out.println("Back button clicked");
            gsm.setState(new MainState(gsm), panel);
        });
    }

    // Link that explains things:
    // https://docs.oracle.com/javase%2Ftutorial%2F/uiswing/components/progress.html 
    private void simulateMonthProgress() {
        // Swingworker to invoke the doInBackground
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            // Overides it tp cpimt to 100, with the thread sleeping in between the counts.
            protected Void doInBackground() throws Exception {
                int count = 0;
                while (count <= 100) {
                    Thread.sleep(5); // Adjust speed of progress bar.
                    publish(count);
                    count++;
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                monthProgress.setValue(value);
            }

            @Override
            protected void done() {
                timeController.incrementMonth();
                update();  // Update the label after month progresses
                gsm.notifyTimeObservers();
            }
        };
        worker.execute();
    }

    @Override
    public void update() {
        feedbackLabel.setText("Time has advanced by one month.");
        ageLabel.setText("Age: " + player.getAge());
        currentMonthLabel.setText("Current Month: " + timeController.getCurrentMonth());
        lastMonthEarningsLabel.setText("Last Month's Earnings: $" + account.getMonthlyEarnings());
    }

    @Override
    public void draw(Graphics g) { }

    @Override
    public void exit() { }
}
