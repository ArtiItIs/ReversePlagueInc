import javax.swing.*;
import java.awt.*;

public class UpgradePointsPanel extends JPanel{

    private JLabel upgradePointsLabel;
    private int upgradePoints;

    public UpgradePointsPanel(){
        this.setLayout(new BorderLayout());
        this.upgradePoints = 0;

        upgradePointsLabel = new JLabel("Upgrade points: " + upgradePoints, SwingConstants.CENTER);
        upgradePointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(upgradePointsLabel, BorderLayout.CENTER);
    }

    //METODA AKTUALIZUJACA LICZBE PKT
    public void updatePointsAfterByuing(int cost){
        upgradePoints = upgradePoints - cost;
        upgradePointsLabel.setText("Upgrade points: " + upgradePoints);
    }

    //GETTER DO POBRANIA LICZBY PKT
    public int getUpgradePoints(){
        return upgradePoints;
    }
}
