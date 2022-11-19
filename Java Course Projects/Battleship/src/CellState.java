package battleship;

public enum CellState {
    EMPTY('~'),
    OCCUPIED('O'),
    MISS('M'),
    HIT('X'),
    ;

    char ch;

    CellState(char ch) {
        this.ch = ch;
    }

    char getCh() {
        return ch;
    }
}
