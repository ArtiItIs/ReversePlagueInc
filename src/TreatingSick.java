import java.util.List;

public class TreatingSick {
    private List<Country> countries;
    private VaccinePanel vaccinePanel;

    public TreatingSick(List<Country> countries, VaccinePanel vaccinePanel){
        this.countries = countries;
        this.vaccinePanel = vaccinePanel;
    }

    //metoda zajmujaca sie leczeniem
    //zalozenie:
    //jesli gdzies w ktoryms panstwie % osob chorych przekroczy 1% populacji z metody sprawdzajacej w klasie Worldmappanel
    //wlacza sie ta metoda, ktora:
    //co 10 sekund bierze 10% chorych danego kraju i zmienia ich z sick na treated
    //nastepnie z tych osob ktorea sa juz treated, wyleczone bedzie tyle ile jest % postepu nad szczepionka
    //jak sie uda to bedzie wolniejsze niz smierc ale smierc jest jeszcze do wymyslenia !!!
    public void treatPeopleMethod(){
        double vaccineProgress = vaccinePanel.getVaccineProgress();

        for( Country counry : countries){

            //zmiana z sick na treated
            int sickToTreat= (int)(counry.getSick()*0.1);//wybiera aktualne 10% chorych
            //zmiana z treated na healthy
            int treatedToHealthy= (int)(counry.getTreated()*(vaccineProgress/100));

            //ustawienie danych
            counry.setSick(counry.getSick() - sickToTreat);
            counry.setTreated(counry.getTreated() + sickToTreat- treatedToHealthy);
            counry.setHealthy(counry.getHealthy() + treatedToHealthy);
        }

    }
}
