package sorting.sorter;

import sorting.sorter.blueprint.Sorter;
import sorting.sorter.sorterData.SortedData;

import java.util.*;

public class LongSorter extends Sorter<Long> {

    @Override
    protected void naturalSort() {
        Collections.sort(data);
    }

    @Override
    protected void countingSort() {
        List<Map.Entry<Long, Integer>> entryList = new ArrayList<>(dataFrequency.entrySet());
        entryList.sort((longIntegerEntry, t1) -> {
            if (longIntegerEntry.getValue().compareTo(t1.getValue()) == 0) {
                return longIntegerEntry.getKey().compareTo(t1.getKey());
            }
            return longIntegerEntry.getValue().compareTo(t1.getValue());
        });
        dataFrequency = new LinkedHashMap<>();
        entryList.forEach(v -> dataFrequency.put(v.getKey(), v.getValue()));
    }

    @Override
    public SortedData<Long> getSortedData(List<Long> inputs, String sortingType) {
        data = new ArrayList<>(inputs);
        dataFrequency = new LinkedHashMap<>();
        inputs.forEach(v -> dataFrequency.merge(v, 1, (v1, v2) -> v1 + 1));
        if (sortingType.equals("natural")) {
            naturalSort();
        } else {
            countingSort();
        }
        return new SortedData<>(dataFrequency, data, inputs.size());
    }

}
