package sorting.sorter;

import sorting.sorter.blueprint.Sorter;
import sorting.sorter.sorterData.SortedData;

import java.util.*;

public class StringSorter extends Sorter<String> {
    @Override
    protected void naturalSort() {
        data.sort(Comparator.comparing(String::length));
    }

    @Override
    protected void countingSort() {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(dataFrequency.entrySet());
        entryList.sort((stringIntegerEntry, t1) -> {
            if (stringIntegerEntry.getValue().compareTo(t1.getValue()) == 0) {
                return stringIntegerEntry.getKey().compareTo(t1.getKey());
            }
            return stringIntegerEntry.getValue().compareTo(t1.getValue());
        });
        dataFrequency = new LinkedHashMap<>();
        entryList.forEach(v -> dataFrequency.put(v.getKey(), v.getValue()));
    }

    @Override
    public SortedData<String> getSortedData(List<String> inputs, String sortingType) {
        data = new ArrayList<>(inputs);
        dataFrequency = new LinkedHashMap<>();
        inputs.forEach(v -> dataFrequency.merge(v, 1, (v1, v2) -> v1 + 1));
        if (sortingType.equals("natural")) {
            naturalSort();
        } else {
            countingSort();
        }
        return new SortedData<>(dataFrequency, data, data.size());
    }
}
