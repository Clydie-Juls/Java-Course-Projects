package sorting.sorter.sorterData;

import java.util.List;
import java.util.Map;

public record SortedData<T>(Map<T, Integer> sortedListFrequency, List<T> sortedList,
                            int totalDataInstance) {

}
