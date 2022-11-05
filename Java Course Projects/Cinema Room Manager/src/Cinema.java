package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get size
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        char[][] seats = new char[rows][seatsPerRow];
        createCinema(rows, seats);
        int choice = -1;
        int ticketsSold = 0;
        int income = 0;

        while (choice != 0) {
            printChoices();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printCinema(rows, seatsPerRow, seats);
                    break;
                case 2:
                    int row = 0;
                    int rowSeat = 0;
                    boolean isAvailable = false;
                    while (!isAvailable) {
                        System.out.println("\nEnter a row number:");
                        row = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        rowSeat = scanner.nextInt();

                        if (row > 0 && row <= rows && rowSeat > 0 && rowSeat <= seatsPerRow) {
                            if (seats[row - 1][rowSeat - 1] == 'S'){
                                isAvailable = true;
                            } else {
                                System.out.println("\nThat ticket has already been purchased!");
                            }
                        } else {
                            System.out.println("\nWrong input!");
                        }
                    }

                    buyTicket(row, rowSeat, ticketPrice(row, rows, seatsPerRow), seats);
                    income += ticketPrice(row, rows, seatsPerRow);
                    ticketsSold++;
                    break;

                case 3:
                    displayStatistics(ticketsSold, income, rows, seatsPerRow);
            }
        }

    }

    static void printChoices() {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    static void createCinema (int rows, char[][] seats) {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(seats[i], 'S');
        }
    }

    static void printCinema(int rows, int seatsPerRow, char[][] seats) {
        System.out.print("\nCinema:\n  ");
        for (int i = 0; i < seatsPerRow; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seatsPerRow; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int ticketPrice(int row, int rows, int seatsPerRow) {
        return rows * seatsPerRow > 60 && row > rows / 2 ? 8 : 10;
    }

    static void buyTicket (int row, int rowSeat, int price, char[][] seats) {
        System.out.println("\nTicket price: $" + price);
        seats[row - 1][rowSeat - 1] = 'B';
    }

    static void displayStatistics(int ticketsSold, int income, int rows, int seatsPerRow) {
        int totalIncome;

        if (rows * seatsPerRow > 60) {
            totalIncome = rows / 2 * seatsPerRow * 10 + (rows - rows / 2) * seatsPerRow * 8;
        } else {
            totalIncome = rows * seatsPerRow * 10;
        }

        System.out.println("\nNumber of purchased tickets: " + ticketsSold);
        System.out.printf("Percentage: %.2f%%\n",(float)ticketsSold / (rows * seatsPerRow) * 100);
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + totalIncome);
    }
}