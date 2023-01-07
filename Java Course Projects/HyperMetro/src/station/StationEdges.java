package metro.station;

public class StationEdges {
    private final StationEdges previousStation;
    private final Station currentStation;

    private final int weight;

    public StationEdges(StationEdges previousStation, Station currentStation, int weight) {
        this.previousStation = previousStation;
        this.currentStation = currentStation;
        this.weight = weight;
    }

    public StationEdges getPreviousStation() {
        return previousStation;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public int getWeight() {
        return weight;
    }
}
