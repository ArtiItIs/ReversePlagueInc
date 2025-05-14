import javax.swing.*;
import java.awt.*;

public class SickCounterPanel extends JPanel{

    private JLabel sickCounterLabel;

    public SickCounterPanel(){
        this.setLayout(new BorderLayout());

        //tymczasowe do zmiany na rzeczywisty licznik
        //int points = 100;

        sickCounterLabel = new JLabel("People sick: ", SwingConstants.CENTER);
        sickCounterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(sickCounterLabel, BorderLayout.CENTER);
    }

    //metoda aktualzjaca ilosc chorych (chorzy i leczeni na raz) i zmarlych na swiecie
    public void counterUpdate(int sickInTheWorld, int deadInTheWorld){
        sickCounterLabel.setText(("People sick: "+ sickInTheWorld + " ("+deadInTheWorld +")"));
    }
}
