package metro.station;

public class ConnectedStation {
    private final String line;
    private final String station;

    public ConnectedStation(String line, String station) {
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }
}
