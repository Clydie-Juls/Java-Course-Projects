package battleship;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Board player1Board;
    private static Board player2Board;

    static int turn = 1;

    public static void main(String[] args) {


        //Deploying of player ships
        System.out.println("Player 1, place your ships on the game field\n");
        player1Board = new Board();
        deployPlayerShips(player1Board);
        System.out.println("Player 2, place your ships to the game field\n");
        player2Board = new Board();
        deployPlayerShips(player2Board);
        System.out.println();

        // Gameplay starts
        while (!player1Board.isSunkAllShips() && !player2Board.isSunkAllShips()) {
            if (turn % 2 == 1) {
                playerTurn(player1Board, player2Board, "Player 1");
            } else {
                playerTurn(player2Board, player1Board, "Player 2");
            }
        }

        // Print the player who won
        System.out.print("You sank the last ship. You won. Congratulations,");
        System.out.println(!player1Board.isSunkAllShips() ? "Player 1!" : "Player 2!");
    }

    private static void deployPlayerShips(Board board) {
        boolean failedInput = false;
        int i = 1;

        while (i <= ShipType.values().length) {
            try {
                ShipType shipType = ShipType.values()[i - 1];
                if (!failedInput) {
                    System.out.printf("Enter the coordinates of the %s (%d cells):\n\n",
                            shipType.shipName, shipType.numberOfShips);
                }

                String input = scanner.nextLine().trim();
                System.out.println();
                board.positionShips(input, shipType.numberOfShips, i - 1);
                i++;
                failedInput = false;
                board.displayBoard();
            } catch (Error e) {
                failedInput = true;
                System.out.println(e.getMessage() + "\n");
            }
        }
        goToNextMove();
    }

    private static void playerTurn(Board board, Board enemyBoard, String playerName) {
        try {
            board.displayGameplayBoard(enemyBoard);
            System.out.println(playerName + ", it's your turn:\n");
            String input = scanner.nextLine().trim();
            System.out.println();
            String message = board.takeShot(input, enemyBoard);
            if (!enemyBoard.isSunkAllShips()) {
                System.out.println(message);
                goToNextMove();
                turn++;
            }
        }catch (Error e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    private static void goToNextMove() {
        System.out.println("Press Enter and pass the move to another player");
        System.out.print("...");
        scanner.nextLine();
    }
}
