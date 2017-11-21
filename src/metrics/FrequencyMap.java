package metrics;


import java.util.TreeMap;

/**
 *
 */
public class FrequencyMap {


    TreeMap<Double,Integer> map = new TreeMap<>();


    public FrequencyMap add(double[] a) {
        for (double x : a) {
            add(x);
        }
        return this;
    }

    public FrequencyMap add(Double x) {
        Integer count = map.get(x);
        if (count == null) {
            count = 0;
        }
        count++;
        map.put(x,count);
        return this;
    }

    public TreeMap getMap() { return map; }

}
