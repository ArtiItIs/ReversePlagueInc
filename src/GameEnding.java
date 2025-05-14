import javax.swing.*;
import java.awt.*;

public class GameEnding {
    private JFrame gameFrame;
    private PointsThreadUpdater pointsThreadUpdater;

    public GameEnding(JFrame gameFrame, PointsThreadUpdater pointsThreadUpdater){
        this.gameFrame = gameFrame;
        this.pointsThreadUpdater = pointsThreadUpdater;
    }

    public void victoryEnding(){
        //stworzenie ekranu koncowego
        JFrame victoryFrame = new JFrame("Finish!");
        victoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        victoryFrame.setSize(555, 333);
        victoryFrame.setLayout(new BorderLayout());

        //imie gracza
        JPanel namePanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel("Nickname: ", SwingConstants.CENTER);
        JTextField nameField = new JTextField();
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameField, BorderLayout.CENTER);
        victoryFrame.add(namePanel, BorderLayout.NORTH);

        //punkty gracza
        JPanel pointPanel = new JPanel();
        pointPanel.setLayout(new BorderLayout());
        int finalPoints = pointsThreadUpdater.getTotalPoints();
        JLabel pointsLabel =new JLabel("Your score: "+ finalPoints, SwingConstants.CENTER);
        pointPanel.add(pointsLabel);
        victoryFrame.add(pointPanel, BorderLayout.CENTER);

        //przycisk zamykajacy gre
        //todo zapisywanie rekordu
        JButton saveButton = new JButton("save");
        saveButton.addActionListener(e -> {
            //pobranie nicku z pola tekstowego
            String nickname = nameField.getText();

            //zamkniecie okna koncowego i okna gry
            victoryFrame.dispose();
            gameFrame.dispose();
        });

        //dodanie przycisku
        victoryFrame.add(saveButton, BorderLayout.SOUTH);

        victoryFrame.setVisible(true);
    }
}
