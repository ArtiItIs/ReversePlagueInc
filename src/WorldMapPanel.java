import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class WorldMapPanel extends JLayeredPane{

    private List<Country> countries;
    private CountryInfoPanel countryInfoPanel;
    private List<Connection> connections;
    private boolean isWorldAlarmed = false; //flaga odpowiedzialna za start leczenia ludzi

    public WorldMapPanel(CountryInfoPanel countryInfoPanel, ClockMainThread clockMainThread, VaccinePanel vaccinePanel){
        this.countryInfoPanel = countryInfoPanel;
        this.setLayout(null);
        this.setOpaque(true);
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.LIGHT_GRAY);
        backgroundPanel.setBounds(0, 0, 800, 600);
        this.add(backgroundPanel, Integer.valueOf(0));

        countries = new ArrayList<>();
        connections = new ArrayList<>();
        initializeCountries();
        initializeConnections();
        renderCountries(); // wyswietlenie krajow jako macierz na JLayeredPane

        //wywolanie watku przejscia z jednego kraju do drugiego
        WorldMapAnimator mapAnimator = new WorldMapAnimator(this, countries, connections);
        mapAnimator.start();

        //wywolanie startu leczenia do zegara
        clockMainThread.addClockListener(globalSeconds -> checkAlarm());

        //automatyczne skalowanie
        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                renderCountries();
            }
        });

        clockMainThread.addClockListener(globalSeconds -> updateCountryImage());
    }

    //metoda sprawdzajaca kiedy wlaczyc alarm, po ktorymz aczyna sie leczenie
    private void checkAlarm(){
        for(Country country:  countries){
            double infecterPercentage = (double)country.getInfected() / country.getPopulation();
            if(infecterPercentage > 0.01){
                isWorldAlarmed = true;
                return;
            }
        }
        isWorldAlarmed=  false;
    }


    //getter do pobrania listy krajow
    public List<Country> getCountries(){
        return countries;
    }

    private void initializeCountries(){
        //macierz krajow
        countries.add(new Country("North America", "Images/North_america_Green.png", 6000000, 0, 0, 0, List.of("Car", "Plane", "Boat")));
        countries.add(new Country("Greenland", "Images/Greenland_Green.png", 60000, 0, 0, 0, List.of("Plane", "Boat")));
        countries.add(new Country("Scandinavia", "Images/Scandinavia_Green.png", 280000, 0, 0, 0, List.of("Car", "Plane", "Boat")));
        countries.add(new Country("Russia", "Images/Russia_Green.png", 1440000, 0, 0, 0, List.of("Car", "Plane", "Boat")));
        countries.add(new Country("China", "Images/China_Green.png", 14000000, 500000, 0, 0, List.of("Car", "Plane", "Boat")));
        countries.add(new Country("South America", "Images/South_America_Green.png", 4400000, 0, 0, 0, List.of("Car", "Plane", "Boat")));
        countries.add(new Country("Africa", "Images/Africa_Green.png", 13750000, 0, 0, 0, List.of("Car", "Plane", "Boat")));
        countries.add(new Country("United Kingdom", "Images/United_Kingdom_Green.png", 690000, 0, 0, 0, List.of("Plane", "Boat")));
        countries.add(new Country("Central Europe", "Images/Central_Europe_Green.png", 5500000, 0, 0, 0, List.of("Car", "Plane", "Boat")));
        countries.add(new Country("Australia", "Images/Australia_Green.png", 270000, 0, 0, 0, List.of("Plane", "Boat")));
    }

    private void initializeConnections(){
        //polaczenia z poszczegolnymi krajami

        //north america
        connections.add(new Connection("North America", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("North America", "Scandinavia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("North America", "Russia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("North America", "China", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("North America", "South America", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("North America", "Africa", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("North America", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("North America", "Central Europe", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("North America", "Australia", Arrays.asList("Boat", "Plane")));

        //greenland
        connections.add(new Connection("Greenland", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "Scandinavia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "Russia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "China", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "Africa", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "Central Europe", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Greenland", "Australia", Arrays.asList("Boat", "Plane")));

        //scandinavia
        connections.add(new Connection("Scandinavia", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "Russia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "China", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "Africa", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "Central Europe", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Scandinavia", "Australia", Arrays.asList("Boat", "Plane")));

        //russia
        connections.add(new Connection("Russia", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Russia", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Russia", "Scandinavia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Russia", "China", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Russia", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Russia", "Africa", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Russia", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Russia", "Central Europe", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Russia", "Australia", Arrays.asList("Boat", "Plane")));

        //china
        connections.add(new Connection("China", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("China", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("China", "Scandinavia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("China", "Russia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("China", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("China", "Africa", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("China", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("China", "Central Europe", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("China", "Australia", Arrays.asList("Boat", "Plane")));

        //south america
        connections.add(new Connection("South America", "North America", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("South America", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("South America", "Scandinavia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("South America", "Russia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("South America", "China", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("South America", "Africa", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("South America", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("South America", "Central Europe", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("South America", "Australia", Arrays.asList("Boat", "Plane")));

        //africa
        connections.add(new Connection("Africa", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Africa", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Africa", "Scandinavia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Africa", "Russia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Africa", "China", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Africa", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Africa", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Africa", "Central Europe", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Africa", "Australia", Arrays.asList("Boat", "Plane")));

        //united kingdom
        connections.add(new Connection("United Kingdom", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "Scandinavia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "Russia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "China", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "Africa", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "Central Europe", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("United Kingdom", "Australia", Arrays.asList("Boat", "Plane")));

        //central europe
        connections.add(new Connection("Central Europe", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Central Europe", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Central Europe", "Scandinavia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Central Europe", "Russia", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Central Europe", "China", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Central Europe", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Central Europe", "Africa", Arrays.asList("Car", "Boat", "Plane")));
        connections.add(new Connection("Central Europe", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Central Europe", "Australia", Arrays.asList("Boat", "Plane")));

        //australia
        connections.add(new Connection("Australia", "North America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "Greenland", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "Scandinavia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "Russia", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "China", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "South America", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "Africa", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "United Kingdom", Arrays.asList("Boat", "Plane")));
        connections.add(new Connection("Australia", "Central Europe", Arrays.asList("Boat", "Plane")));
    }



    private void renderCountries(){
        this.removeAll();

        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        int rows = 2;
        int cols = 5;
        int padding = 20;

        if(panelWidth <= 0 || panelHeight <= 0){
            System.out.println("Panel dimension too small");
            return;
        }

        int countryWidth = Math.max(1, (panelWidth - (cols + 1) * padding) / cols);
        int countryHeight = Math.max(1, (panelHeight - (rows + 1) * padding) / rows);

        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);

            int row = i / cols;
            int col = i % cols;

            int x = padding + col * (countryWidth + padding);
            int y = padding + row * (countryHeight + padding);

            System.out.println("Loading image for: " + country.getName() + " Path: " + country.getImagePath());

            country.setBounds(x,y, countryWidth,countryHeight);

            //wywolanie metody ktora bedzie wybierac jakie zdjecie bedzie mial kraj
            String imagePath = getCountryImagePath(country);

            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/" + imagePath));
            if (originalIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Failed to load image for: " + country.getName());
            }

            Image scaledImage = originalIcon.getImage().getScaledInstance(countryWidth, countryHeight, Image.SCALE_SMOOTH);

            JLabel countryLabel = new JLabel(new ImageIcon(scaledImage));
            countryLabel.setBounds(x, y, countryWidth, countryHeight);

            //nazwa kraju jako tooltip
            countryLabel.setToolTipText(country.getName());

            countryLabel.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    updateCountryInfo(country);
                }
            });

            //dodanie kraju do layered pane na 1 warstwe
            this.add(countryLabel, Integer.valueOf(1));

            System.out.printf("Dodawanie kraju: %s - Bounds: x=%d, y=%d, w=%d, h=%d%n",
                    country.getName(), x, y, countryWidth, countryHeight);


        }
        this.revalidate();
        this.repaint();
    }

    private void updateCountryImage(){
        for( Country cuontry : countries){

            String newImagePath = getCountryImagePath(cuontry);

            JLabel countryLabel = findCountryLabel(cuontry.getName());

            if(countryLabel != null){
                ImageIcon originalIcon = new ImageIcon(getClass().getResource("/" + newImagePath));
                Image scaledImage = originalIcon.getImage().getScaledInstance(cuontry.getBounds().width,
                        cuontry.getBounds().height, Image.SCALE_SMOOTH);
                countryLabel.setIcon(new ImageIcon(scaledImage));
            }
        }
        this.revalidate();
        this.repaint();
    }

    private JLabel findCountryLabel(String countryName){
        for(Component component : this.getComponents()){
            if(component instanceof JLabel){
                JLabel componentLabel = (JLabel) component;
                if(countryName.equals(componentLabel.getToolTipText())){
                    return componentLabel;
                }
            }
        }
        return null;
    }

    //metoda wybierajaca kolor kraju (zdjecie) w zaleznosci od % osob chorujacych
    private String getCountryImagePath(Country country){
        double infectedPercentage = (double)(country.getInfected() + country.getDead()) / country.getPopulation();
        if(infectedPercentage > 0.75){
            return country.getImagePath().replace("_Green.png", "_Red.png");
        }else if(infectedPercentage > 0.30){
            return country.getImagePath().replace("_Green.png", "_Yellow.png");
        }else{
            return country.getImagePath();
        }
    }

    //aktualizacja informacji o kraju w panelu countryInfoPanel
    private void updateCountryInfo(Country country){
        countryInfoPanel.updateCountryInfo(
                country.getName(),
                country.getAlive(),
                country.getSick(),
                country.getTreated(),
                country.getDead(),
                calcucateNotInfectedPercentage(country),
                calculateInfectedPercentage(country),
                String.join(", ", country.getTransportTypes())
        );
    }

    //metoda zliczajaca osoby zywe (w tym zdrowe)
    public int[] getAliveHealthyCounter(){
        int aliveInTheWorld = 0;
        int healthyInTheWorld = 0;

        for(Country country : countries){
            aliveInTheWorld = aliveInTheWorld + country.getAlive();
            healthyInTheWorld = healthyInTheWorld + country.getAlive() - country.getSick() - country.getTreated();
        }
        return new int[]{aliveInTheWorld, healthyInTheWorld};
    }

    //metoda zliczajaca osoby chore (oraz martwe) na swiecie
    public int[] getSickDeadCounter(){
        int sickInTheWorld = 0;
        int deadInTheWorld = 0;

        for(Country coutnry : countries){
            sickInTheWorld = sickInTheWorld + coutnry.getSick() + coutnry.getTreated();
            deadInTheWorld = deadInTheWorld + coutnry.getDead();
        }
        return new int[]{sickInTheWorld, deadInTheWorld};
    }

    //obliczanie procent niezainfekowanych
    private double calcucateNotInfectedPercentage(Country country){
        int alive = country.getAlive();
        int notInfected = country.getHealthy();

        if(alive == 0){
            return 0;
        }

        return ((double) notInfected / alive) * 100;
    }

    public double calculateInfectedPercentage(Country country){
        int alive = country.getAlive();
        int infected = country.getInfected();

        if(alive == 0){
            return 0;
        }

        return ((double) infected / alive) * 100;
    }

    //metoda wylaczajaca transport danego kraju po osiagnieciu konkretnej ilosci zarazonych
    public void updateTransportRestriction(){
        for(Country country: countries){
            double infectedPercentage = calculateInfectedPercentage(country);

            if(infectedPercentage > 80){
                country.disableTransport("Boat");
            }
            if(infectedPercentage > 50){
                country.disableTransport("Plane");
            }
            if(infectedPercentage > 20){
                country.disableTransport("Car");
            }
        }
    }

    //metoda wylaczajaca transport DO danego kraju po osiagnieciu konkretnej ilosci zarazonych
    public void updateConnectionRestroction(){
        for(Country country: countries){
            double infectedPercentage = calculateInfectedPercentage(country);

            if(infectedPercentage > 80){
                disableConnection(country, "Boat");
            }
            if(infectedPercentage > 50){
                disableConnection(country, "Plane");
            }
            if(infectedPercentage > 20){
                disableConnection(country, "Car");
            }
        }
    }

    private void disableConnection(Country country, String transportType){
        country.disableTransport(transportType);

        for(Connection connection: connections){
            if(connection.getFromCountry().equals(country.getName()) || connection.getToCountry().equals(country.getName())){
//                List<String> transportTypes = connection.getTransportTypes();
                connection.getTransportTypes().removeIf(type -> type.equals(transportType));
            }
        }
    }


    //metoda zabijajaca wszystkich ludzi po zakupieniu ulepszenia
    //KILL'EM ALL
    public void killTheWorld(){
        for(Country country : countries){
            int alive = country.getAlive();
            if(alive > 0){
                country.setDead(country.getDead() + alive);
                country.setAlive(0);

            }
        }
    }
}