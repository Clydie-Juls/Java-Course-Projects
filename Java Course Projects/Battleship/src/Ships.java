package battleship;

public class Ships {
    private int startCell = 0;
    private int endCell = 0;

    private int linePos = 0;

    int[] cellPositions;

    private boolean isSunk = false;
    private boolean isAlreadySunk = false;

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    public boolean isAlreadySunk() {
        return isAlreadySunk;
    }

    public void setAlreadySunk(boolean alreadySunk) {
        isAlreadySunk = alreadySunk;
    }

    public int getLinePos() {
        return linePos;
    }

    private boolean isVertical;

    int getStartCell() {
        return startCell;
    }

    void setStartCell(int startCell) {
        this.startCell = startCell;
    }

    int getEndCell() {
        return endCell;
    }

    void setEndCell(int endCell) {
        this.endCell = endCell;
        sort();
    }

    boolean isVertical() {
        return isVertical;
    }

    void setVertical(boolean vertical, String pos) {
        isVertical = vertical;

        if (!vertical) {
            linePos = pos.charAt(0) - 'A' + 1;
        } else {
            linePos = Integer.parseInt(pos.substring(1));
        }
    }

    private void sort() {
        if(startCell >= endCell) {
            int dummy = startCell;
            startCell = endCell;
            endCell = dummy;
        }
    }
}
