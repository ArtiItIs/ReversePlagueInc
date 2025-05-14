import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Country {

    private String name;
    private String imagePath;
    private int alive;
    private int sick;
    //private int healthy;
    private int treated;
    private int dead;
    private List<String> transportTypes;
    //private boolean transportOpen = true; //do wylaczania transportu miedzy krajami
//    private Set<String> availableTransportTypes;
    private int x;
    private int y;
    private int width;
    private int height;


    public Country(String name, String imagePath, int alive, int sick,
                   int treated, int dead, List<String> transportTypes){
        this.name = name;
        this.imagePath = imagePath;
        this.alive = Math.max(0, alive);
        this.sick = Math.max(0, sick);
        this.treated = Math.max(0, treated);
        this.dead = Math.max(0, dead);
        this.transportTypes = new ArrayList<>(transportTypes);
        //this.availableTransportTypes = new HashSet<>(transportTypes); //zainicjowanie zmiennej tak zeby na starcie miala dostepne wszystkie opcje
        //this.healthy = alive - sick - treated;
        settingBounds();
    }

    private void settingBounds(){
        int maxSickTreated = alive;
        sick = Math.min(sick, maxSickTreated);
        treated = Math.min(treated, maxSickTreated - sick);
    }

    //gettery
    public String getName(){
        return name;
    }

    public String getImagePath(){
        return imagePath;
    }

    public int getPopulation(){
        return alive + dead;
    }

//    private int calculateHealthy(){
//        return Math.max(0, alive - sick - treated);
//    }

    public int getAlive(){
        return alive;
    }

    public void setAlive(int alive){
        this.alive = Math.max(0, alive);
        settingBounds();
    }

    public int getSick(){
        return sick;
    }

    public void setSick(int sick){
        this.sick = Math.max(0, sick);
        settingBounds();
    }

    public int getHealthy(){
        return Math.max(0, alive - sick - treated);
    }

    public void setHealthy(int healthy){
        healthy = Math.max(0,healthy);
        if(healthy > alive){
            healthy = alive;
        }

        this.sick = alive - healthy - treated;
    }

    public int getTreated(){
        return treated;
    }

    public void setTreated(int treated){
        this.treated = Math.max(0,treated);
        settingBounds();
    }

    public int getInfected(){
        return sick + treated;
    }

//    public void setInfected(int infected){
//        infected = Math.max(0, infected);
//
//        if(infected > alive){
//            infected = alive;
//        }
//
//        this.sick = infected - treated;
//        if(this.sick < 0){
//            this.sick = 0;
//        }
//    }

    public int getDead(){
        return dead;
    }

    public void setDead(int dead){
        this.dead = Math.max(0, dead);
    }

    public List<String> getTransportTypes(){
        return transportTypes;
    }

    public void disableTransport(String transportType){
        if(transportTypes.contains(transportType)){
            transportTypes= new ArrayList<>(transportTypes); //zmienna przehowujaca kopie listy
            transportTypes.remove(transportType);
        }
    }


    public void setBounds(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    //metoda zwracajaca srodek do ktorego ma przejsc animacja ikony
    public Point getCenter(){
        int centerX = x + width / 2;
        int centerY = y + height / 2;

        return new Point(centerX, centerY);
    }
}
