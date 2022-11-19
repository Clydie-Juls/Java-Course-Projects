package battleship;


public class Board {
    final private CellState[][] board;
    Ships[] allShips;

    Board() {
        board = new CellState[10][10];
        allShips = new Ships[5];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = CellState.EMPTY;
            }
        }
        displayBoard();
    }

    void displayBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < board.length; i++) {
            System.out.print((char) (i + 'A'));
            for (int j = 0; j < board.length; j++) {
                System.out.print(" " + board[i][j].getCh());
            }
            System.out.println();
        }
        System.out.println();
    }

    void displayGameplayBoard(Board enemyBoard) {
        // Enemy player board
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < enemyBoard.board.length; i++) {
            System.out.print((char) (i + 'A'));
            for (int j = 0; j < enemyBoard.board.length; j++) {
                System.out.print(enemyBoard.board[i][j] == CellState.EMPTY ||
                        enemyBoard.board[i][j] == CellState.OCCUPIED
                        ? " " + CellState.EMPTY.getCh() :" " + enemyBoard.board[i][j].getCh());
            }
            System.out.println();
        }
        System.out.println("---------------------");
        // Player board
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < board.length; i++) {
            System.out.print((char) (i + 'A'));
            for (int j = 0; j < board.length; j++) {
                System.out.print(" " + board[i][j].getCh());
            }
            System.out.println();
        }
        System.out.println();
    }

    boolean isSunkAllShips() {
        boolean allShipsSunken = true;
        for (int i = 0; i < allShips.length; i++) {
            boolean hasOccupiedCell = false;
            for (int j = 0; j < allShips[i].cellPositions.length; j++) {
                int x =  (allShips[i].cellPositions[j] - 1) % 10 + 1;
                int y =  (allShips[i].cellPositions[j] - 1) / 10 + 1;
                if (board[y - 1][x - 1] == CellState.OCCUPIED) {
                    hasOccupiedCell = true;
                    allShipsSunken = false;
                }
            }
            allShips[i].setSunk(!hasOccupiedCell);
        }
        return allShipsSunken;
    }

    void positionShips(String input, int cellsPerShipType, int index) throws Error{
        Ships ships = getShipsPositions(input, cellsPerShipType);
        ships.cellPositions = new int[cellsPerShipType];
        for (int i = ships.getStartCell(); i <= ships.getEndCell(); i++) {
            if (!ships.isVertical()){
                board[ships.getLinePos() - 1][i - 1] = CellState.OCCUPIED;
                ships.cellPositions[i - ships.getStartCell()] = i + (ships.getLinePos() - 1) * board.length;
            } else {
                board[i - 1][ships.getLinePos() - 1] = CellState.OCCUPIED;
                ships.cellPositions[i - ships.getStartCell()] = ships.getLinePos() + (i - 1) * board.length;
            }
        }
        allShips[index] = ships;
    }

    String takeShot (String input, Board enemyBoard) throws Error {
        int column = getCoordinate(true, input);
        int row = getCoordinate(false, input);
        if (enemyBoard.board[column - 1][row - 1] == CellState.EMPTY) {
            enemyBoard.board[column - 1][row - 1] = CellState.MISS;
            return "You missed!";
        }
        enemyBoard.board[column - 1][row - 1] = CellState.HIT;
        enemyBoard.isSunkAllShips();
        for (int i = 0; i < enemyBoard.allShips.length; i++) {
            if (enemyBoard.allShips[i].isSunk() && !enemyBoard.allShips[i].isAlreadySunk()) {
                enemyBoard.allShips[i].setAlreadySunk(true);
                return "You sank a ship!";
            }
        }
        return "You hit a ship!";
    }

    private int getCoordinate(boolean isColumn, String coordinate) throws Error {
        if (isColumn) {
           int column = coordinate.charAt(0);
           if (column < 'A' || column > 'J') {
               throw new Error("Error! Out of bounds in column! Try again:");
           }
            return column - 'A' + 1;
        } else {
            int row = Integer.parseInt(coordinate.substring(1));
            if (row < 1 || row > 10){
                throw new Error("Error! Out of bounds in row! Try again:");
            }
            return row;
        }
    }

    private Ships getShipsPositions(String input, int cellsPerShipType) throws Error{
        Ships ships = new Ships();
        String[] positions = input.toUpperCase().split(" "); //Sample correct format: F3 F7
        int startCell;
        int endCell;

        int startColumn, endColumn, startRow, endRow;

        startColumn = getCoordinate(true, positions[0]);
        endColumn = getCoordinate(true, positions[1]);
        startRow = getCoordinate(false, positions[0]);
        endRow = getCoordinate(false, positions[1]);

        if (startRow != endRow && startColumn != endColumn) {
            throw new Error("Error! Wrong ship positioning! Try again:");
        }

        ships.setVertical(positions[0].charAt(0) != positions[1].charAt(0), positions[0]);
        startCell = ships.isVertical() ? startColumn : startRow;
        endCell = ships.isVertical() ? endColumn : endRow;


        if(Math.abs(startCell - endCell) + 1 != cellsPerShipType) {
            throw new Error("Error! Incorrect length of ships! Try again:");
        }

        ships.setStartCell(startCell);
        ships.setEndCell(endCell);

        if (isNearAdjacentCells(ships.getStartCell(), ships.getEndCell(), ships.isVertical(), ships.getLinePos())) {
            throw new Error("Error! Too close to other ships! Try again:");
        }

        return ships;
    }

    boolean isNearAdjacentCells(int startCell, int endCell, boolean isVertical, int linePos){
        for (int i = startCell - 1; i <= endCell + 1; i++) {
            for (int j = linePos - 1; j <= linePos + 1; j++) {
                if (i >= 1 && i <= 10 && j >= 1 && j <= 10) {
                    if (!((j == linePos - 1 || j == linePos + 1) && (i == startCell - 1 || i == endCell + 1))) {
                        if (!isVertical) {
                            if (board[j - 1][i - 1].getCh() == 'O') {
                                return true;
                            }
                        } else {
                            if (board[i - 1][j - 1].getCh() == 'O') {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}