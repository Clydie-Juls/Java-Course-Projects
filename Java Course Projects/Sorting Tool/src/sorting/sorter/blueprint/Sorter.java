package sorting.sorter.blueprint;

import sorting.sorter.sorterData.SortedData;

import java.util.List;
import java.util.Map;

public abstract class Sorter <T> {
    protected Map<T, Integer> dataFrequency;
    protected List<T> data;

    protected abstract void naturalSort();

    protected abstract void countingSort();
    public abstract SortedData<T> getSortedData(List<T> inputs, String sortingType);
}
