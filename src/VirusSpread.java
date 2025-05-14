import java.util.List;

public class VirusSpread {

    private List<Country> countryList;

    public VirusSpread(List<Country> countryList){
        this.countryList = countryList;
    }

    //todo wrzucic metode jako listenera co glownego watku
    //metoda z obliczeniami do rozprzestrzenienia choroby
    public void virusSpread(){

        for(Country country : countryList){
            int peopleSick = country.getSick();
            int peopleHealthy = country.getAlive() - country.getTreated() - country.getSick();
            int peopleToInfect = 0;

            //losowanie ile osob ma sie zarazic
            if(peopleSick > 0) {
                peopleToInfect = (int) ((1 + Math.random() * (peopleSick / 4 )));
            }

            //zabezpieczenie przed wykroczeniem poza max pozostalych niezarazonych ludzi
            if(peopleToInfect > peopleHealthy){
                peopleToInfect = peopleHealthy;
            }

            //wywolanie setterow aktualizujacych dane
            country.setSick(peopleSick + peopleToInfect);
            country.setHealthy(country.getHealthy() - peopleToInfect);


        }
    }
}
