import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DifficultyPanel extends JPanel{

    private JLabel difficultyLabel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;

    private int buttonWidth = 150;
    private int buttonHeight = 50;
    private int buttonSpacing = 20;
    public DifficultyPanel(JFrame difficultyFrame){
        this.setLayout(null);

        //napis na gorze okna
        difficultyLabel = new JLabel("Choose difficulty", SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(difficultyLabel);  //dodanie napisu

        //przyciski pod napisem
        easyButton = newDifficultyButton("Easy", difficultyFrame);
        normalButton = newDifficultyButton("Normal", difficultyFrame);
        hardButton = newDifficultyButton("Hard", difficultyFrame);

        //dodanie przyciskow
        this.add(easyButton);
        this.add(normalButton);
        this.add(hardButton);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                updateButtonPosition();
            }
        });
    }
    private JButton newDifficultyButton(String difficulty, JFrame difficultyFrame){
        JButton button = new JButton(difficulty);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(Color.WHITE);
        button.setOpaque(true); //nieprzezroczystosc
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chosen difficulty: " + difficulty);
            difficultyFrame.dispose();
            ReversePlagueInc.getInstance().startNewGameDifficulty(difficulty);
        });
        return button;
    }

    //aktualizuje pozycje przyciskow i napisu
    private void updateButtonPosition(){
        int myPanelWidth = this.getWidth();
        int myPanelHeight = this.getHeight();

        //wysrodkowanie etykiety difficulty
        int labelWidth = 300;
        int labelHeight = 50;
        int labelXcenter = myPanelWidth / 2 - labelWidth / 2;
        int labelYcenter = myPanelHeight / 6; // 1/6 sprawi ze napis bedzie na gorze
        difficultyLabel.setBounds(labelXcenter, labelYcenter, labelWidth, labelHeight);

        //wysrodkowanie przyciskow
        //zeby przycisk byl na srodku X
        int buttonXcenter = myPanelWidth / 2 - buttonWidth / 2;
        //zeby przycisk pierwszy zaczynal od srodka Y
        int buttonYfirst = myPanelHeight / 2 - ((buttonHeight * 3 + buttonSpacing * 2) / 4);

        easyButton.setBounds(buttonXcenter, buttonYfirst, buttonWidth, buttonHeight);
        normalButton.setBounds(buttonXcenter, buttonYfirst + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
        hardButton.setBounds(buttonXcenter, buttonYfirst + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
    }
}
