import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.SystemColor.text;

public class MainMenuPanel extends JPanel {

    private JButton newGameButton;
    private JButton rankingButton;
    private JButton exitButton;

    //przyciski
    private int buttonWidth = 200;
    private int buttonHeight = 50;
    private int buttonSpacing = 20;

    public MainMenuPanel(){
        this.setLayout(null);

        newGameButton = newMenuButton("New Game");
        rankingButton = newMenuButton("Ranking");
        exitButton = newMenuButton("Exit");

        //logika przycisku New Game

        //tworzymy nowa instancje JFrame, ktora jest odpowiedzialna za wyswietlenie
        //okna wyboru poziomu trudnosci przed rozpoczeciem gry
        //W momencie tworzenia okna dodawany jest JPanel
        newGameButton.addActionListener(e -> {
            JFrame difficultyFrame = new JFrame("Select Difficulty");
            difficultyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            difficultyFrame.setSize(300, 400);
            difficultyFrame.setLocationRelativeTo(null);
            difficultyFrame.add(new DifficultyPanel(difficultyFrame));
            difficultyFrame.setVisible(true);
        });

        //logika przycisku Ranking
        rankingButton.addActionListener(e -> {
            //
        });

        //logika przycisku Exit
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        this.add(newGameButton);
        this.add(rankingButton);
        this.add(exitButton);

        //zmiana wielkosci przyciskow przy zmianie wielkosci okna
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                updateButtonPosition();
            }
        });
    }
    private JButton newMenuButton(String text){
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(Color.WHITE);
        button.setOpaque(true); //nieprzezroczystosc
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        return button;
    }

    private void updateButtonPosition(){
        int myPanelWidth = this.getWidth();
        int myPanelHeight = this.getHeight();

        //zeby przycisk byl na srodku X
        int buttonXcenter = myPanelWidth / 2 - buttonWidth / 2;
        //zeby przycisk pierwszy zaczynal od srodka Y
        int buttonYfirst = myPanelHeight / 2 - ((buttonHeight * 3 + buttonSpacing * 2) / 3);

        newGameButton.setBounds(buttonXcenter, buttonYfirst, buttonWidth, buttonHeight);
        rankingButton.setBounds(buttonXcenter, buttonYfirst + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
        exitButton.setBounds(buttonXcenter, buttonYfirst + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
    }
}
