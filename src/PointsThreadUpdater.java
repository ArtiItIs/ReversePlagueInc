import javax.swing.*;

public class PointsThreadUpdater implements ClockMainThread.ThreadListener{

    private int points;
    private JLabel pointsLabel;

    public PointsThreadUpdater(JLabel pointsLabel){
        this.points = 10000;
        this.pointsLabel = pointsLabel;
    }

    @Override
    public void secondTick(int globalSeconds){
        if (globalSeconds % 3 == 0){
            points = points - 10;

            if(points < 0){
                points = 0;
            }

            SwingUtilities.invokeLater(() -> pointsLabel.setText("Points: " + points));
        }
    }

    //punkty do koncowego wyniku gry
    public int getTotalPoints(){
        return points;
    }
}
