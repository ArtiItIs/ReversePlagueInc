import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorldMapAnimator extends Thread{

    private JLayeredPane layeredPane;
    private List<Country> countries;
    private List<Connection> connections;
    //private int animationDelay = 10;
    private int animationSteps = 100;

    //ustawienie predkosci srodkow transportu
    private int boatSpeed = 30;
    private int carSpeed = 20;
    private int planeSpeed = 10;

    public WorldMapAnimator(JLayeredPane layeredPane, List<Country> countries, List<Connection> connections){
        this.layeredPane = layeredPane;
        this.countries = countries;
        this.connections = connections;
    }

    @Override
    public void run() {
        for (Country country : countries) {
            new Thread(() -> {
                while (true) {
                    try {
                        int sleepTime = 1000 + (int) (Math.random() * 3000);
                        Thread.sleep(sleepTime);
                        if (Math.random() < 0.5) {
                            chooseVehicle(country);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //wlaczenie watku
            }).start();
        }
    }

    //losuje ktory pojazd oraz gdzie ma wyruszyc oraz wywoluje metode rozpoczynajaca animacje
    private void chooseVehicle(Country startCountry){
        //losowanie rodzaju transportu do animacji
        if(startCountry.getTransportTypes().contains("Boat") && Math.random() < 0.5){
            //Country endCountry = gambleCountryDestination(startCountry);
            destinationFilter(startCountry, "Boat", boatSpeed);
        }
        if(startCountry.getTransportTypes().contains("Car") &&Math.random() < 0.5){
            //Country endCountry = gambleCountryDestination(startCountry);
            destinationFilter(startCountry,"Car", carSpeed);
        }
        if(startCountry.getTransportTypes().contains("Plane") &&Math.random() < 0.5){
            //Country endCountry = gambleCountryDestination(startCountry);
            destinationFilter(startCountry, "Plane", planeSpeed);
        }
    }

//    private Country gambleCountryDestination(Country startCountry){
//        int startIndex = countries.indexOf(startCountry);
//        int endIndex;
//
//        //petla ktora uniemozliwi wybranie tego samego indeksu konscowego co poczatkowy
//        do{
//            endIndex =(int)(Math.random()* countries.size());
//        }while(endIndex == startIndex);
//
//        return countries.get(endIndex);
//    }

    private void destinationFilter(Country startCountry, String vehicleType, int speed){
        List<Country> availableDestination = new ArrayList<>();

        //niesamowita super giga wielka petla sprawdzajaca wszystkie mozliwe polaczenia
        //DZIALA Gfughfdibhgifipubdhfavfnbfdsbsbsbiufdsbvdsru3h4w9f8eguhf
        for(Connection connection : connections){
            if(connection.getFromCountry().equals(startCountry.getName()) && connection.getTransportTypes().contains(vehicleType)){
                for( Country country : countries) {
                    if (country.getName().equals(connection.getToCountry() ) && country.getTransportTypes().contains(vehicleType)){
                        availableDestination.add(country);
                    }
                }
            }
        }


        //to jesli nie ma juz gdzei wyruszyc
        if(availableDestination.isEmpty()){
            System.out.println(startCountry + " all borders were closed :c");
            return;
        }

        //zmienna losyje kraj do ktorego wyruszyc z listy dostepnych krajow
        Country endCountry = availableDestination.get((int)(Math.random()* availableDestination.size()));


        //wywolanie metody tworzacej obiekt z watkiem animacji
        makeObjectWithThread(startCountry, endCountry, vehicleType, speed);
    }

    //tworzy obiekt z watkiem animacji
    private void makeObjectWithThread(Country startCountry, Country endCountry, String vehicleType, int speed){

        int startX = startCountry.getBounds().x + startCountry.getBounds().width/2;
        int startY = startCountry.getBounds().y + startCountry.getBounds().height /2;
        int endX = endCountry.getBounds().x+ endCountry.getBounds().width / 2;
        int endY = endCountry.getBounds().y + endCountry.getBounds().height /2;

        //ustawia zdjecie do konkretnego rodzaju pojazdu
        String vehicleImagePath ="/Images/" + vehicleType + "_Icon_Front.png";
        ImageIcon vehicleIconToScale = new ImageIcon(getClass().getResource(vehicleImagePath));
        Image scaledVehicleImage = vehicleIconToScale.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon vehicleIcon = new ImageIcon(scaledVehicleImage);

        //utworzenie nowego obiektu z pojazdem
        JLabel movingObject = new JLabel(vehicleIcon);
        movingObject.setBounds(startX, startY, 25,25);
        layeredPane.add(movingObject, Integer.valueOf(2));

        //utworzenie watku z animacja
        new Thread(() -> animateObject(movingObject, startCountry, endCountry, startX, startY, endX, endY, speed)).start();
    }

    private void animateObject( JLabel object, Country startCountry, Country endCountry, int startX, int startY, int endX, int endY, int speed){
        for (int i = 0; i < animationSteps; i++) {
            double time = (double) i / animationSteps;
            int currentX = (int) (startX + time * (endX - startX));
            int currentY = (int) (startY + time * (endY - startY));

            SwingUtilities.invokeLater(() -> object.setLocation(currentX, currentY));

            try{
                Thread.sleep(speed);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        //sprawi ze obiekt usunie sie po dotarciu do celu
        SwingUtilities.invokeLater(() -> {
                layeredPane.remove(object);
                layeredPane.revalidate();
                layeredPane.repaint();
        });

        spreadVirus(startCountry, endCountry);
    }

    //metoda do przenoszenia wirusa miedzy krajami
    private void spreadVirus(Country startCountry, Country endCountry){
        double infectedPercentage = (startCountry.getInfected() / (double)startCountry.getAlive()) * 500;

        //losowanie czy wirus zostnaie przeniesiony do innego kraju (mr. worldwide wannabe)
        if(Math.random() < infectedPercentage){
            if(endCountry.getSick() ==0){
                endCountry.setSick(1);
            }else{
                endCountry.setSick(endCountry.getSick() + (1+(int)(50* infectedPercentage)));
            }
        }
    }
}
