package bullscows;

import java.util.Scanner;

public class ErrorPrinter {

    static void tryPrintError(StringBuilder s, int len) {
        try {
            s.charAt(len - 1);
        } catch (Exception e) {
            System.out.println("Error: it's not possible to generate a code with a " +
                    "length of " + len + " with " + s.length() + " unique symbols.");
            System.exit(0);
        }
    }

    static int tryPrintError() {
        String s  = new Scanner(System.in).nextLine();
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            System.out.println("Error: \"" + s + "\" isn't a valid number.");
            System.exit(0);
            return 0;
        }
    }

    static void tryPrintError(int len) {
        try {
            "0123456789abcdefghijklmnopqrstuvwxyz".charAt(len - 1);
        } catch (Exception e) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
    }
}
