package chucknorris.src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String action;
        do {
            System.out.println("Please input operation (encode/decode/exit):");
            action = scanner.nextLine();
            if (action.equals("encode")) {
                System.out.println("Input string:");
                String str = scanner.nextLine();
                System.out.println("Encoded string:");
                encryption(str);
                System.out.println("\n");
            } else if (action.equals("decode")) {
                System.out.println("Input encoded string:");
                String str = scanner.nextLine();
                System.out.print(decryption(str));
                System.out.println("\n");
            } else if (action.equals("exit")){
                System.out.println("Bye!");
            } else {
                System.out.println("There is no '" + action + "' operation\n");
            }
        } while (!action.equals("exit"));
    }

    private static void encryption (String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String binary = String.format("%07d", Integer.parseInt(Integer.toBinaryString(str.charAt(i))));
            stringBuilder.append(binary);
        }
        for (int j = 0; j < stringBuilder.length();) {
            char ch = stringBuilder.charAt(j);
            System.out.print(ch == '0' ? "00 " : "0 ");
            while (stringBuilder.charAt(j) == ch) {
                System.out.print("0");
                j++;
                if (j == stringBuilder.length()) {
                    break;
                } else {
                    if (ch != stringBuilder.charAt(j)) {
                        System.out.print(" ");
                    }
                }
            }
        }
    }

    private static String decryption(String str){
        String[] code =  str.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        if (code.length % 2 == 1) {
            return "Encoded string is not valid.";
        }
        for (int i = 0; i < code.length; i++) {
            String digit = code[i].length() == 1 ? "1" : "0";
            if (!code[i].equals("0") && !code[i].equals("00") || !code[i].matches("[0]+")) {
                return "Encoded string is not valid.";
            }

            stringBuilder.append(digit.repeat(code[i + 1].length()));
            i++;
        }
        String binary = stringBuilder.toString();
        StringBuilder decryptedString = new StringBuilder();

        if (binary.length() % 7 != 0) {
            return "Encoded string is not valid.";
        }

        for (int i = 0; i < binary.length() / 7; i++) {
            try {
                int num = Integer.parseInt(binary.substring(i * 7, (i + 1) * 7), 2);
                decryptedString.append((char) num);
            } catch (Exception e) {
                return "Encoded string is not valid.";
            }
        }

        return "Decoded string:\n" + decryptedString;
    }
}