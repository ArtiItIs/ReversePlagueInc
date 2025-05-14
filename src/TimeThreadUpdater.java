import javax.swing.*;

public class TimeThreadUpdater implements ClockMainThread.ThreadListener{
    private int secondsCounter;
    private JLabel timeLabel;

    public TimeThreadUpdater(JLabel timeLabel){
        this.secondsCounter = 0;
        this.timeLabel = timeLabel;
    }

    @Override
    public void secondTick(int globalSeconds){
        this.secondsCounter = globalSeconds;

        int minutes = secondsCounter / 60;
        int seconds = secondsCounter % 60;

        //invokeLater dodaje zadanie do kolejki kontynuujac dzialanie watku
        //lambda w tym przypadku jest odpowiednikiem funkcji "Runnable"
        //String.format pozwala na wprowadzenie formatu ciagu znakow
        //%d - integery, 02 - ilosc cyfr (z czego otrzymujemy 0 z przodu jesli nie ma 2 cyfr)
        SwingUtilities.invokeLater(() -> timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)));

    }
}
