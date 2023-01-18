package sorting.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private static List<String> args;

    private static String getDataType() throws Exception {
        if (args.contains("-dataType")) {
            if (args.indexOf("-dataType") != args.size() - 1) {
                String type = args.get(args.indexOf("-dataType") + 1);
                if (type.equals("word") || type.equals("long") || type.equals("line")) {
                    return type;
                }
            }
            throw new Exception("No data type defined!");
        }
        return "word";
    }

    private static String getSortingType() throws Exception {
        if (args.contains("-sortingType")) {
            if (args.indexOf("-sortingType") != args.size() - 1) {
                String type = args.get(args.indexOf("-sortingType") + 1);
                if (type.equals("natural") || type.equals("byCount")) {
                    return type;
                }
            }
            throw new Exception("No sorting type defined!");
        }
        return "natural";
    }

    private static File getInputFile() throws IOException {
        if (args.contains("-inputFile")) {
            if (args.indexOf("-inputFile") != args.size() - 1) {
                String path = args.get(args.indexOf("-inputFile") + 1);
                File file = new File(path);
                if (file.exists() && !file.isDirectory()) {
                    return file;
                }

            }
            throw new IOException("No input file defined!");
        }
        return null;
    }

    private static File getOutputFile() throws IOException {
        if (args.contains("-outputFile")) {
            if (args.indexOf("-outputFile") != args.size() - 1) {
                String path = args.get(args.indexOf("-outputFile") + 1);
                File file = new File(path);
                file.createNewFile();
                return file;
            }
            throw new IOException("No output file defined! ");
        }
        return null;
    }

    public static CommandArgs getArguments(String[] commandArgs) throws Exception {
        args = new ArrayList<>(Arrays.stream(commandArgs).toList());
        CommandArgs commArgs = new CommandArgs(getDataType(), getSortingType(),
                getInputFile(), getOutputFile());
        Pattern pattern = Pattern.compile("-\\w+");
        for (String str: commandArgs) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches() && !str.equals("-sortingType") && !str.equals("-dataType")) {
                System.out.printf("\"%s\" is not a valid parameter. It will be skipped\n", str);
            }
        }
        return commArgs;
    }
}
