package sorting;

import sorting.command.*;
import sorting.sorter.*;
import sorting.input.InputDataParser;
import sorting.sorter.sorterData.SortedData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            CommandArgs commandArgs = CommandParser.getArguments(args);
            scanner = commandArgs.inputFile() != null ? new Scanner(commandArgs.inputFile()) :
                    new Scanner(System.in);
            switch (commandArgs.dataType()) {
                case "long":
                    test(new LongSorter().getSortedData(
                                    InputDataParser.getLong(scanner), commandArgs.sortingType()),
                            commandArgs, commandArgs.outputFile());
                    break;
                case "word":
                    test(new StringSorter().getSortedData(
                                    InputDataParser.getWords(scanner), commandArgs.sortingType()),
                            commandArgs, commandArgs.outputFile());
                    break;
                case "line":
                    test(new LineSorter().getSortedData(
                                    InputDataParser.getLines(scanner), commandArgs.sortingType()),
                            commandArgs, commandArgs.outputFile());
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            scanner.close();
        }
    }

    private static <T> void test(SortedData<T> sortedData, CommandArgs types, File outputFile) {
        StringBuilder stringBuilder = new StringBuilder();
        String dataType = types.dataType().equals("long") ? "numbers" :
                types.dataType().equals("word") ? "words" : "lines";
        stringBuilder.append(String.format("Total %s: %d.\n", dataType,
                sortedData.totalDataInstance()));
        if (types.sortingType().equals("natural")) {
            stringBuilder.append("Sorted data:");
            if (types.dataType().equals("line")) {
                sortedData.sortedList().forEach(v -> stringBuilder.append(v).append("\n"));
            } else {
                sortedData.sortedList().forEach(v -> stringBuilder.append(" ").append(v));
                stringBuilder.append("\n");
            }
        } else {
            sortedData.sortedListFrequency().forEach((k, v) -> {
                String output = k + ": " + v + " time(s), " +
                        Math.round(v / (float)sortedData.totalDataInstance() * 100) + "%";
                stringBuilder.append(output).append("\n");
            });
        }

        if (outputFile == null) {
            System.out.print(stringBuilder);
        } else {
            try (FileWriter fileWriter = new FileWriter(outputFile)){
                fileWriter.write(stringBuilder.toString());
            } catch (IOException e) {
                System.out.println("FileWriter Error");
            }
        }
    }
}
