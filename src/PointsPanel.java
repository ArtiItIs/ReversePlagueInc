import javax.swing.*;
import java.awt.*;

public class PointsPanel extends JPanel{

    private JLabel pointsLabel;
    //private Thread pointsThread;

    public PointsPanel(ClockMainThread clockMainThread){
        this.setLayout(new BorderLayout());

        pointsLabel = new JLabel("Points: 10000", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(pointsLabel, BorderLayout.CENTER);

        PointsThreadUpdater pointsUpdater = new PointsThreadUpdater(pointsLabel);
        clockMainThread.addClockListener(pointsUpdater);

//        //metoda uruchamiajaca watek
//        updatingPointsMethod();
    }

    //tworzy watek i go rozpoczyna (watek sluzy aktualizacji punktow podczas gry)
//    private void updatingPointsMethod(){
//        pointsThread = new Thread(new PointsThreadUpdater(pointsLabel));
//        pointsThread.start();
//    }
}
