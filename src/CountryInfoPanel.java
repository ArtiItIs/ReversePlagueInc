import javax.swing.*;
import java.awt.*;

public class CountryInfoPanel extends JPanel{

    //zmienne do labeli
    private JLabel countryNameLabel;
    private JLabel aliveLabel;
    private JLabel sickLabel;
    private JLabel treatedLabel;
    private JLabel deadLabel;
    private JLabel notInfectedLabel;
    private JLabel infectedLabel;
    private JLabel transportLabel;

    //panele dla obrazow dostepnego transportu
    private JLabel carLabel;
    private JLabel boatLabel;
    private JLabel planeLabel;

    public CountryInfoPanel(){
        this.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(100, getHeight()));
        this.setMinimumSize(new Dimension(100, getHeight()));
        this.setMaximumSize(new Dimension(100, getHeight()));

        //napis z nazwa kraju
        countryNameLabel = new JLabel("Country name: ", SwingConstants.CENTER);
        countryNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(countryNameLabel, BorderLayout.NORTH);

        //informacje o stanie danego kraju
        JPanel countryInfoPanel = new JPanel();
        countryInfoPanel.setLayout(new GridLayout(7, 1, 5, 0));
        aliveLabel = new JLabel("Alive: ");
        sickLabel = new JLabel("Sick: ");
        treatedLabel = new JLabel("Treated: ");
        deadLabel = new JLabel("Dead: ");
        notInfectedLabel = new JLabel("% of not infected: ");
        infectedLabel = new JLabel("% of infected: ");
        transportLabel = new JLabel("Types of transport: ");

        countryInfoPanel.add(aliveLabel);
        countryInfoPanel.add(sickLabel);
        countryInfoPanel.add(treatedLabel);
        countryInfoPanel.add(deadLabel);
        countryInfoPanel.add(notInfectedLabel);
        countryInfoPanel.add(infectedLabel);
        countryInfoPanel.add(transportLabel);

        this.add(countryInfoPanel, BorderLayout.CENTER);

        //zdjecia na spodzie panelu z dostepnymi srodkami transporty
        JPanel transportImagesPanel = new JPanel();
        transportImagesPanel.setLayout(new GridLayout(1, 3, 0, 0));
        transportImagesPanel.setPreferredSize(new Dimension(100, 50));

        //pliki ze zdjeciami POBRAC I ZALADOWAC TUTAJ
        carLabel = new JLabel();
        boatLabel = new JLabel();
        planeLabel = new JLabel();

        transportImagesPanel.add(carLabel);
        transportImagesPanel.add(boatLabel);
        transportImagesPanel.add(planeLabel);

        this.add(transportImagesPanel, BorderLayout.SOUTH);
    }

    //metoda aktualizujaca informacje o kraju
    public void updateCountryInfo(String countryName, int alive, int sick, int treated, int dead,
                                  double notInfectedPercentage, double infectedPercentage, String transportTypes){

        countryNameLabel.setText("Country name: " + countryName);
        aliveLabel.setText("Alive: " + alive);
        sickLabel.setText("Sick: " + sick);
        treatedLabel.setText("Treated: " + treated);
        deadLabel.setText("Dead: " + dead);
        notInfectedLabel.setText("% of not infected: " + String.format("%.2f", Math.max(0, 100 - infectedPercentage)) + "%");
        infectedLabel.setText("% of infected: " + String.format("%.2f", Math.min(100, infectedPercentage))+ "%");
        transportLabel.setText("Types of transport available: " + transportTypes);

        updateTransportImages(transportTypes);
    }

    private void updateTransportImages(String transportTypes){
        carLabel.setIcon(getTransportIcon("Car", transportTypes.contains("Car")));
        boatLabel.setIcon(getTransportIcon("Boat", transportTypes.contains("Boat")));
        planeLabel.setIcon(getTransportIcon("Plane", transportTypes.contains("Plane")));

    }

    //getter do ikon transportow
    private ImageIcon getTransportIcon(String transportType, boolean isAvailable){

        String iconPath;

        if(isAvailable){
            iconPath = "/Images/" + transportType + "_Icon_Front.png";
        } else {
            iconPath = "/Images/" + transportType + "_Icon_Inactive.png";
        }

        ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));

        //skalowanie ikon
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }
}
