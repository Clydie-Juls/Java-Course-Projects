package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        int len = ErrorPrinter.tryPrintError();
        System.out.println("Input the number of possible symbols in the code:");
        int symbolLength = ErrorPrinter.tryPrintError();

        String code = "";
        String guess = "";

        if (len <= 36) {
            code = generatePseudoRandomNumber(len, symbolLength);
            System.out.println("Okay, let's start a game!");
            String possibleChars = symbolsIncluded(symbolLength);
            System.out.printf("The secret is prepared: %s %s.\n", "*".repeat(len), possibleChars);
            System.out.println("Okay, let's start a game!");
        } else {
            System.out.printf("Error: can't generate a secret number with" +
                    " a length of %d because there aren't enough " +
                    "unique digits.\n", len);
        }

        int turn = 1;

        while (!code.equals(guess)) {
            System.out.println("Turn " + turn + ":");
            turn++;
            guess = scanner.next();
            displayGrade(code, guess, len);
        }
    }

    static String symbolsIncluded(int symbolLength) {
        String nm1 = "(0-";
        char nm2 = (char) Math.min(57, 47 + symbolLength);
        String symb1 = symbolLength == 10 ? ", a" : symbolLength >= 11 ? ", a-" : "";
        String symb2 = symbolLength >= 11 ? String.valueOf((char) (86 + symbolLength)) : "";
        return nm1 + nm2 + symb1 + symb2 + ")";
    }

    static String generatePseudoRandomNumber (int length, int symbolLength) {
        ErrorPrinter.tryPrintError(symbolLength);
        StringBuilder text = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyz".substring(0, symbolLength));
        StringBuilder code = new StringBuilder();

        ErrorPrinter.tryPrintError(text, length);

        for (int i = 0; i < length; i++) {
            int rand = (int)(Math.random() * text.length());
            char ch = text.charAt(rand);
            code.append(ch);
            text.deleteCharAt(rand);
        }

        return code.toString();
    }
    
    static void displayGrade(String code, String guess, int codeLength){
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (code.charAt(i) == guess.charAt(i)) {
                bulls++;
            } else if (code.indexOf(guess.charAt(i)) != -1) {
                cows++;
            }
        }
        String bullsText = bulls == 1 ? bulls + " bull" : bulls > 1 ? bulls + " bulls" : "";
        String cowsText = cows == 1 ? cows + " cow" : cows > 1 ? cows + " cows" : "";
        if(bulls != 0 || cows != 0) {
            System.out.printf("Grade: %s%s%s.\n", bullsText, bulls != 0 && cows != 0 ? " and " : "", cowsText);
        } else {
            System.out.print("Grade: None.");
        }
        if (bulls == codeLength) {
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }
}
