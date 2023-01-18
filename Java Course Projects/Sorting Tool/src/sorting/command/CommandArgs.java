package sorting.command;

import java.io.File;

public record CommandArgs(String dataType, String sortingType, File inputFile, File outputFile) {


}
