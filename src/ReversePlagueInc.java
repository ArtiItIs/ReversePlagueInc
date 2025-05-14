import javax.swing.*;

public class ReversePlagueInc {

    private static ReversePlagueInc instance;
    private MainMenuPanel menuPanel;

    private MyFrame menuFrame;

    private ReversePlagueInc(){
        menuPanel = new MainMenuPanel();
        menuFrame = new MyFrame(menuPanel);
        menuFrame.add(menuPanel);
    }

    //singleton
    public static ReversePlagueInc getInstance(){
        if(instance == null){
            instance = new ReversePlagueInc();
        }
        return instance;
    }

    //tworzymy nowa instancje JFrame zawierajaca sama gre
    public void startNewGameDifficulty(String difficulty){
        JFrame gameFrame = new JFrame ("Reverse Plague Inc.");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1400, 1000);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setContentPane(new GamePanel(difficulty));
        gameFrame.setVisible(true);
    }
}
