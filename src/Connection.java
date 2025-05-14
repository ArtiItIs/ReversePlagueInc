import java.util.ArrayList;
import java.util.List;

public class Connection {
    private String fromCountry; //lraj startowy
    private String toCountry; //kraj docelowy
    private List<String> transportTypes;

    public Connection(String fromCountry, String toCountry, List<String> transportTypes){
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.transportTypes = new ArrayList<>(transportTypes);
    }

    public String getFromCountry(){
        return fromCountry;
    }

    public String getToCountry(){
        return toCountry;
    }

    public List<String> getTransportTypes(){
        return transportTypes;
    }

    public void removeTransportType(String transportType){
        transportTypes.remove(transportType);
    }
}
