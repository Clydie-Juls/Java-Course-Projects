package metro.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    public static String[] parseCommand(String command) throws Exception {
        List<String> inputs = new ArrayList<>();
        Matcher matcher = Pattern.compile("([^\\s\"]+|\"[^\"]*\")").matcher(command);
        while (matcher.find()) {
            inputs.add(matcher.group().replace("\"","").trim());
        }
        return inputs.toArray(new String[0]);
    }
}
