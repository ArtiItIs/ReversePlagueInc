import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upgrades {

    private Map<String, Integer> upgrades;
    private Map<String, Boolean> bought;

    public Upgrades(){
        upgrades = new HashMap<>();
        bought = new HashMap<>();


        //mapa ulepszen (nazwa, koszt)
        upgrades.put("Heal the world", 0);
        upgrades.put("Kill the world", 0);
        upgrades.put("Upgrade 3", 1);
        upgrades.put("Upgrade 4", 0);
        upgrades.put("Upgrade 5", 0);
        upgrades.put("Upgrade 6", 0);
        upgrades.put("Upgrade 7", 0);
        upgrades.put("Upgrade 8", 0);
        upgrades.put("Upgrade 9", 0);

        //ustawia booleana domyslnie na false
        for(String upgrade: upgrades.keySet()){
            bought.put(upgrade, false);
        }

    }

    public int getUpgracePrice(String upgrade){
        return upgrades.getOrDefault(upgrade, 0);
    }

    public boolean isBought(String upgrade){
        return bought.getOrDefault(upgrade, false);
    }

    public boolean buyUpgrade(String upgrade){
        if(upgrades.containsKey(upgrade) && !bought.get(upgrade)){
            bought.put(upgrade, true);
            return true;
        }
        return false;
    }

    public void addUpgrade(String upgradeName, int upgradeCost){
        if(!upgrades.containsKey(upgradeName)){
            upgrades.put(upgradeName, upgradeCost);
        }
    }

    public String[] getAvaiableUpgrades(){
        List<String> avaiableUpgrades = new ArrayList<>();

        for(String upgrade : upgrades.keySet()){
            if(!bought.get(upgrade)){
                avaiableUpgrades.add(upgrade);
            }
        }
        return avaiableUpgrades.toArray(new String[0]);
    }
}
