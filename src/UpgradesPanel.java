import javax.swing.*;
import java.awt.*;
public class UpgradesPanel extends JPanel{

    private JList<String> upgradesList;
    private JButton purchaseButton;
    private Upgrades upgrades;
    private UpgradePointsPanel pointsPanel;

    public UpgradesPanel(Upgrades upgrades, UpgradePointsPanel pointsPanel){
        this.setLayout(new BorderLayout());
        this.upgrades = upgrades;
        this.pointsPanel = pointsPanel;

        //czesc panelu z JList ktora bedzie przechowywala liste ulepszen
        upgradesList = new JList<>(upgrades. getAvaiableUpgrades());
        JScrollPane scrollPane = new JScrollPane(upgradesList);
        this.add(scrollPane, BorderLayout.CENTER);

        //czesc panelu z przyciskiem do kupienia ulepszenia
        purchaseButton = new JButton("Purchase");
        purchaseButton.setPreferredSize(new Dimension(this.getWidth(), 76));
        //kupienie ulepszenia
        purchaseButton.addActionListener(e -> buySelectedUpgrade());
        this.add(purchaseButton, BorderLayout.SOUTH);
    }

    //kupienie ulepszenia
    private void buySelectedUpgrade(){
        String selectedUpgrade = upgradesList.getSelectedValue();

        int upgradeCost = upgrades.getUpgracePrice(selectedUpgrade);

        if(pointsPanel.getUpgradePoints() < upgradeCost){
            JOptionPane.showMessageDialog(this, "not enough points");
            return;
        }

        if(upgrades.buyUpgrade(selectedUpgrade)){
            pointsPanel.updatePointsAfterByuing(upgradeCost);
            JOptionPane.showMessageDialog(this, "Upgrade purchased: " + selectedUpgrade + "!");
            updateUpdateList();
        }
    }

    public JList<String> getUpgradesList(){
        return upgradesList;
    }

    private void updateUpdateList(){
        upgradesList.setListData(upgrades.getAvaiableUpgrades());
    }

    public JButton getPurchaseButton(){
        return purchaseButton;
    }
}
