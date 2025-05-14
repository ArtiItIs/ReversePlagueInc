import javax.swing.*;
import java.awt.*;

public class VaccinePanel extends JPanel{

    private JLabel vaccineProgressLabel;
    private double vaccineProgress;
    private double progressSpeed = 0.1;
    private ClockMainThread clockMainThread;
    private GameEnding gameEnding;

    public VaccinePanel(ClockMainThread clockMainThread, GameEnding gameEnding){
        this.clockMainThread = clockMainThread;
        this.gameEnding = gameEnding;
        this.vaccineProgress = 0.0; //startowy postep

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200, 50));

        //tekst panelu
        vaccineProgressLabel = new JLabel("Vaccine Progress: 0,0%", SwingConstants.CENTER);
        vaccineProgressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(vaccineProgressLabel, BorderLayout.CENTER);

        //aktualizacja progresu na podstawie glownego watku zegara
        startVaccineUpdater();
    }

    //funkcja aktualizujaca progres
    private void updateVaccineProgress(double vaccineIncrementation){
        if (vaccineProgress < 100.0){
            vaccineProgress = Math.min(vaccineProgress + vaccineIncrementation, 100.0);
            //format %.1f%% = .1 (zaokraglenie do 1 miejsca po przecinku), f (zmiennoprzecinkowa), % na koncu
            vaccineProgressLabel.setText(String.format("Vaccine progress: %.1f%%", vaccineProgress));

            if(vaccineProgress == 100){
                SwingUtilities.invokeLater(() -> gameEnding.victoryEnding());
            }
        }
    }

    //getter progresu szczepionki
    public double getVaccineProgress(){
        return vaccineProgress;
    }

    //funkcja wlaczajaca update progresu (patrzy na glowny zegar)
    private void startVaccineUpdater(){
        clockMainThread.addClockListener(globalSeconds -> {
            if (globalSeconds % 5 == 0){
                updateVaccineProgress(progressSpeed);
            }
        });
    }

    //metoda wywolana po zakupie ulepszenia
    public void vaccineHealTheWorld(){
        vaccineProgress = 99.7;
    }
}