package app.collection;

import java.util.Comparator;
import java.util.Map;

public class ComparatorClimate implements Comparator<Map.Entry<Integer,City>>{

    @Override
    public int compare(Map.Entry<Integer, City> o1, Map.Entry<Integer, City> o2) {
        return o1.getValue().compareTo(o2.getValue().getClimate().getNumber());
    }
}
