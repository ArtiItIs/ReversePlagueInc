import java.util.ArrayList;
import java.util.List;

public class ClockMainThread extends Thread{

    private List<ThreadListener> listeners = new ArrayList<>();
    private int globalSeconds = 0; //licznik czasu dla reszty watkow

    public interface ThreadListener{
        void secondTick(int globalSeconds);
    }

    //metoda dodajaca watek sluchajacy
    public void addClockListener(ThreadListener listener){
        listeners.add(listener);
    }



    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            try{
                Thread.sleep(1000);
                globalSeconds++;

                for(ThreadListener listener : listeners){
                    listener.secondTick(globalSeconds);
                }
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

}
