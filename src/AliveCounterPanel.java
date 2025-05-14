import javax.swing.*;
import java.awt.*;

public class AliveCounterPanel extends JPanel{

    private JLabel aliveCounterLabel;

    public AliveCounterPanel(){
        //wyglad panelu z labelem wyswietlajacym ilosc osob zywych(w tym zdrowych)
        this.setLayout(new BorderLayout());
        aliveCounterLabel = new JLabel("People alive: ", SwingConstants.CENTER);
        aliveCounterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(aliveCounterLabel, BorderLayout.CENTER);
    }

    //METODA AKTUALIZUJACA LICZBE zywych (w tym zdrowych)
    public void counterUpdate(int aliveInTheWorld, int healthyInTheWorld){
        aliveCounterLabel.setText("People alive: "+ aliveInTheWorld + " ("+healthyInTheWorld + ")");
    }
}
