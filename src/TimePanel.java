import javax.swing.*;
import java.awt.*;

public class TimePanel extends JPanel{

    private JLabel timeLabel;
//    private Thread timeThread;

    public TimePanel(ClockMainThread clockMainThread){
        this.setLayout(new BorderLayout());

        timeLabel = new JLabel("Time: 00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(timeLabel, BorderLayout.CENTER);

        TimeThreadUpdater timeUpdater = new TimeThreadUpdater(timeLabel);
        clockMainThread.addClockListener(timeUpdater);
//        //metoda rozpoczynajaca watek ktory wlacza licznik czasu
//        startTimer();
    }

    //utworzenie watku ktory wlacza licznik czasu
//    private void startTimer(){
//        timeThread = new Thread(new TimeThreadUpdater(timeLabel));
//        timeThread.start();
//    }
}
