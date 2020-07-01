package app.collection;

import java.util.Comparator;

public class ComparatorPopulation implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return Integer.compare(o1.getPopulation(), o2.getPopulation());
    }
}
