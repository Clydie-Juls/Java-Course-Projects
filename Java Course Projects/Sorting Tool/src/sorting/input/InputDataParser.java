package sorting.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputDataParser {

    public static List<String> getLines(Scanner scanner) {
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        return list;
    }

    public static List<String> getWords(Scanner scanner) {
        List<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.next());
        }
        return list;
    }

    public static List<Long> getLong(Scanner scanner) {
        List<Long> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("-?\\d+");
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (pattern.matcher(str).matches()) {
                list.add(Long.parseLong(str));
            } else {
                System.out.println("\""+ str + "\" is not a long. It will be skipped.");
            }
        }

        return list;
    }
}
