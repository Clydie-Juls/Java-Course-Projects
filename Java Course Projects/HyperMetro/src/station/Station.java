package metro.station;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private final String name;
    private String line;
    public List<String> prev;
    public List<String> next;
    public List<ConnectedStation> transfer;

    private final int time;

    public Station(String name, String line, int time) {
        this.name = name;
        this.line = line;
        transfer = new ArrayList<>();
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getLine() {
        return line;
    }

    public int getTime() {
        return time;
    }

    public void setLine(String line) {
        this.line = line;
    }


}

