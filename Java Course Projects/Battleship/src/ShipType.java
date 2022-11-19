package battleship;

public enum ShipType {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);
    String shipName;
    int numberOfShips;
    ShipType(String shipName, int numberOfShips) {
        this.shipName = shipName;
        this.numberOfShips = numberOfShips;
    }
}
