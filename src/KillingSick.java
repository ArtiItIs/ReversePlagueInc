import java.util.List;

public class KillingSick {
    private List<Country> countries;
    private boolean isKillingOn = false;
    private WorldMapPanel worldMapPanel;

    public KillingSick(List<Country> countries, WorldMapPanel worldMapPanel){
        this.countries = countries;
        this.worldMapPanel =worldMapPanel;
    }

    public void startKillingMode(){
        isKillingOn = false;

        for(Country cuontry : countries){
            double infectedPercentage = worldMapPanel.calculateInfectedPercentage(cuontry);

            //zaczyna zabijanei chorych dopiero jesli w jakims kraju jest co najmniej 20% infected
            if(infectedPercentage > 20){
                isKillingOn =true;
                break;
            }
        }
    }

    //metoda ktora ma zabijac odpowiednia ilosc ludzi
    public void humanTerminationMethod(){
        if(!isKillingOn){
            return;
        }

        for (Country country: countries){
            double infectedPercentage = worldMapPanel.calculateInfectedPercentage(country);
            int sick = country.getSick();
//            int treated = country.getTreated();
            int alive = country.getAlive();
            int deaths = 0; //to bedzie dopiero do wyliczenia

            //petla if ktora sprawzda jak wiele ma usmiercac osob co 5 sekung
            if(infectedPercentage >= 80 ){
                deaths = (int)(sick*0.02 +country.getPopulation()*0.01); //najszybsze zeby rozpedzic gre

            }else if(infectedPercentage >=50){
                deaths = (int)(sick*0.02);
            }else if(infectedPercentage >= 20){
                deaths = (int)(sick*0.01);
            }

            //brak mozliwosci zabicia wiecej niz jest zywych
            deaths = Math.min(deaths, alive);


            //aktualizacja danych kraju
            country.setDead(country.getDead()+deaths);
            country.setAlive(alive-deaths);
            country.setSick(Math.max(0,sick-deaths));

        }
    }

    //zwraca wartosc boolean do klasy game panel zeby wywolac metode humanTermination
    public boolean isKillingOn(){
        return isKillingOn;
    }
}
