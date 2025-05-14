import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame {

    //zainicjowanie zmiennej watku glownego ClockMainThread
    private ClockMainThread clockMainThread;
    private AliveCounterPanel aliveCounterPanel;
    private SickCounterPanel sickCounterPanel;
    private WorldMapPanel worldMapPanel;
    private VirusSpread virusSpread;
    private VaccinePanel vaccinePanel;
    private TreatingSick treatingSick;
    private KillingSick killingSick;
    private UpgradePointsPanel upgradePointsPanel;
    private UpgradesPanel upgradesPanel;
    private Upgrades upgrades;
    private PointsThreadUpdater pointsThreadUpdater;
    private GameEnding gameEnding;

    public GamePanel(String difficulty) {
        this.setTitle("Game - Difficulty: " + difficulty);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLayout(new BorderLayout());

        //utworzenie watku glownego ClockMainThread
        clockMainThread = new ClockMainThread();

        //tworzenie ulepszen
        upgrades = new Upgrades();
        upgradePointsPanel = new UpgradePointsPanel();


        JPanel uiRowPanel = createUiRowPanel();
        JPanel upgradeMapRowPanel = createUpgradeMapRowPanel();
        JPanel infoRowPanel = createInfoRowPanel();

        //ustawienie wysokosci pierwszego i trzeciego wiersza
        //sa to wiersze glownie pod UI
        uiRowPanel.setPreferredSize(new Dimension(this.getWidth(), 76));
        infoRowPanel.setPreferredSize(new Dimension(this.getWidth(), 76));

        this.add(uiRowPanel, BorderLayout.NORTH);
        this.add(upgradeMapRowPanel, BorderLayout.CENTER);
        this.add(infoRowPanel, BorderLayout.SOUTH);

        initializeMechanics();
        upgradesMethod();


        //DO WATKU GLOWNEGO dodanie listenera klasy 1killingSick
        clockMainThread.addClockListener(globalSeconds -> {
            int sickWorldwide = 0;
            int deadWorldwide = 0;

            for(Country country: worldMapPanel.getCountries()){
                sickWorldwide = sickWorldwide + country.getSick() + country.getTreated();
                deadWorldwide = deadWorldwide + country.getDead();
            }

//            System.out.println("Teraz sie wywoluje: " + globalSeconds);

            sickCounterPanel.counterUpdate(sickWorldwide, deadWorldwide);
        });

        //wlaczenie glownego watku zegara
        clockMainThread.start();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initializeMechanics(){
        //DO WATKU GLOWNEGO dodanie leczenia do zegara (jako listener jest dodany w glownym zegarze)
        treatingSick = new TreatingSick(worldMapPanel.getCountries(), vaccinePanel);
        clockMainThread.addClockListener(globalSeconds -> {
            if(globalSeconds % 10 ==0){
                treatingSick.treatPeopleMethod();
            }
        });

        //DO WATKU GLOWNEGO dodanie zabijania do zegara (jako listener jest dodany w glownym zegarze)
        killingSick = new KillingSick(worldMapPanel.getCountries(), worldMapPanel);
        clockMainThread.addClockListener(globalSeconds -> {
            if (globalSeconds % 5 ==0){
                killingSick.startKillingMode();
                if(killingSick.isKillingOn()){
                    killingSick.humanTerminationMethod();
                }
            }
        });

        //DO WATKU GLOWNEGO zmiana dostepnych transportow na swiecie
        //transport restriction uniemozliwia zarazonemu panstwu tworzenia konkretnych transportow
        //connection restriction uniemozliwia wyslanie transportu do zarazonego panstwa
        clockMainThread.addClockListener(globalSeconds -> {
            if(globalSeconds % 3 == 0){
                worldMapPanel.updateTransportRestriction();
                worldMapPanel.updateConnectionRestroction();
            }
        });

        //DO WATKU GLOWNEGO dodanie listenera VirusSpread
        virusSpread = new VirusSpread(worldMapPanel.getCountries());
        clockMainThread.addClockListener(globalSeconds -> {
            if(globalSeconds % 3 == 0){
                //metoda nazwana tak jak zmienna (ZMIENIC)
                virusSpread.virusSpread();
            }
        });

        //DO WATKU GLOWNEGO dodanie listenera AliveCounterPanel
        clockMainThread.addClockListener(globalSeconds -> {
            int[] counterAlive = worldMapPanel.getAliveHealthyCounter();
            int aliveInTheWorld = counterAlive[0];
            int healthyInTheWorld = counterAlive[1];
            int[] counterSick = worldMapPanel.getSickDeadCounter();
            int sickInTheWorld = counterSick[0];
            int deadInTheWordl = counterSick[1];

            //wywolanie metody aktualizujacej dane dla zywych
            aliveCounterPanel.counterUpdate(aliveInTheWorld, healthyInTheWorld);
            //wywolanie metody aktualizujacej dane dla chorych
            sickCounterPanel.counterUpdate(sickInTheWorld, deadInTheWordl);
        });

//        //DO WATKU GLOWNEGO koniec gry ENDING
//        clockMainThread.addClockListener(globalSeconds -> {
//            if(vaccinePanel.getVaccineProgress() == 100){
//                gameEnding.victoryEnding();
//            }
//        });
    }

    private void upgradesMethod(){
        upgradesPanel.getPurchaseButton().addActionListener(e ->{
            String selectedUpgrade = upgradesPanel.getUpgradesList().getSelectedValue();

            int upgradeCost = upgrades.getUpgracePrice(selectedUpgrade);

            if(upgradePointsPanel.getUpgradePoints() < upgradeCost){
                JOptionPane.showMessageDialog(this, "Need more points :c");
                return;
            }

            //kupienie ulepszenia
            //po zakupieniu ulepszenia, sprawdza ktore to bylo ulepszenie
            //po sprawdzeniu ktore to bylo, wprowadza je w zucie
            boolean canBeBought = upgrades.buyUpgrade(selectedUpgrade);
            if(canBeBought){
                //aktualizuje punkty
                upgradePointsPanel.updatePointsAfterByuing(upgradeCost);

                //aktualizuje liste
                upgradesPanel.getUpgradesList().setListData(upgrades.getAvaiableUpgrades());

                switch (selectedUpgrade) {
                    case "Heal the world" -> vaccinePanel.vaccineHealTheWorld();
                    case "Kill the world" -> worldMapPanel.killTheWorld();
                }

                JOptionPane.showMessageDialog(this, "Upgrade purchased: " + selectedUpgrade + "!");
            }


        });
    }

    //metoda tworzaca pierwszy wiersz, w layoucie grid bag ktory pozwala na niestandardowe rozmiary w gridzie
    private JPanel createUiRowPanel(){
        JPanel uiRowPanel = new JPanel();
        uiRowPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; //wypelnienie dostepnej przestrzeni panelami
        gbc.weighty = 1.0;//okreslaa rozlozenie wysokosci w wierszu

        //ustawienia pierwszego panelu z iloscia punktow ulepszen
        gbc.gridx = 0;
        gbc.weightx = 1.0;//ma zajac reszte miejsca w wierszu
        gbc.ipadx = 0;

        upgradePointsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uiRowPanel.add(upgradePointsPanel, gbc);

        //ustawienia drugiego panelu z iloscia zywych osob
        gbc.gridx = 1;
        gbc.weightx = 1.0;//ma zajac reszte miejsca w wierszu
        gbc.ipadx = 0;

        aliveCounterPanel = new AliveCounterPanel();
        aliveCounterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uiRowPanel.add(aliveCounterPanel, gbc);

        //ustawienia trzeciego panelu z iloscia chorych osob
        gbc.gridx = 2;
        gbc.weightx = 1.0;//ma zajac reszte miejsca w wierszu
        gbc.ipadx = 0;

        sickCounterPanel = new SickCounterPanel();
        sickCounterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uiRowPanel.add(sickCounterPanel, gbc);

        //ustawienia czwartego panelu z licznikiem punktow
        gbc.gridx = 3;
        gbc.weightx = 1.0;//ma zajac reszte miejsca w wierszu
        gbc.ipadx = 0;

        JLabel pointsLabel = new JLabel("Points: 10000", SwingConstants.CENTER);
        pointsThreadUpdater = new PointsThreadUpdater(pointsLabel);
        clockMainThread.addClockListener(pointsThreadUpdater);

        PointsPanel row1pointsPanel = new PointsPanel(clockMainThread);
        row1pointsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uiRowPanel.add(row1pointsPanel, gbc);

        //ustawienia piatego panelu z licznikiem czasu
        gbc.gridx = 4;
        gbc.weightx = 0;
        gbc.ipadx = 140; //szerokosc panelu w pikselach

        TimePanel row1timePanel = new TimePanel(clockMainThread);
        row1timePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        uiRowPanel.add(row1timePanel, gbc);

        return uiRowPanel;
    }

    //metoda tworzaca drugi wiersz, w layoucie grid bag ktory pozwala na niestandardowe rozmiary w gridzie
    private JPanel createUpgradeMapRowPanel() {
        JPanel upgradeMapRowPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; //wypelnienie dostepnej przestrzeni panelami
        gbc.weighty = 1.0; //okreslaa rozlozenie wysokosci w wierszu

        //ustawienia pierwszego panelu ulepszen
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.ipadx = 200; //szerokosc panelu w pikselach

        upgradesPanel = new UpgradesPanel(upgrades, upgradePointsPanel);
        upgradesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        upgradeMapRowPanel.add(upgradesPanel, gbc);

        //ustawienia drugiego panelu z mapa gry
        gbc.gridx = 1;
        gbc.weightx = 1.0;//ma zajac reszte miejsca w wierszu
        //gbc.ipadx = 0;

//        Dimension windowSize = this.getSize();
//        int remainingHeight = windowSize.height - 76 - 76;

        //zainicjowanie trzeciego panelu poniewaz przekazujemy jego informacje do drugiego panelu
        CountryInfoPanel row2countryInfoPanel = new CountryInfoPanel();

//        //
//        vaccinePanel = new VaccinePanel(clockMainThread);

        vaccinePanel = new VaccinePanel(clockMainThread, gameEnding);
        worldMapPanel = new WorldMapPanel(row2countryInfoPanel, clockMainThread, vaccinePanel);
        //row2MapPanel.setPreferredSize(new Dimension(windowSize.width - 420, remainingHeight));
        worldMapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        upgradeMapRowPanel.add(worldMapPanel, gbc);

        //ustawienie trzeciego panelu z informacjami o obszarze/kraju
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.ipadx = 220; //szerokosc panelu w pikselach

        row2countryInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        upgradeMapRowPanel.add(row2countryInfoPanel, gbc);

        return upgradeMapRowPanel;
    }

    //metoda tworzaca trzeci wiersz w layoucie grid bag ktory pozwala na niestandardowe rozmiary w gridzie
    private JPanel createInfoRowPanel() {
        JPanel infoRowPanel = new JPanel();
        infoRowPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; //wypelnienie dostepnej przestrzeni panelami
        gbc.weighty = 1.0; //okreslaa rozlozenie wysokosci w wierszu

        //ustawienie pierwszego panelu z % postepem w wynajdowaniu leku
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.ipadx = 250;//szerokosc panelu w pikselach

        gameEnding = new GameEnding(this, pointsThreadUpdater);

        vaccinePanel = new VaccinePanel(clockMainThread, gameEnding);
        vaccinePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoRowPanel.add(vaccinePanel, gbc);

        //ustawienie drugiego panelu z Informacjami pojawiajacymi sie podczas gry
        gbc.gridx = 1;
        gbc.weightx = 1.0;//ma zajac reszte miejsca w wierszu
        gbc.ipadx = 0;

        JPanel row3InformationPanel = new JPanel();
        row3InformationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoRowPanel.add(row3InformationPanel, gbc);

        return infoRowPanel;
    }

}