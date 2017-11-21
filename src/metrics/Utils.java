package metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    // Normalizes a value between its MIN and MAX.
    public static double normalise(double a_value, double a_min, double a_max)
    {
        if(a_min < a_max)
            return (a_value - a_min)/(a_max - a_min);
        else    // if bounds are invalid, then return same value
            return a_value;
    }

    // Calculate entropy
    public static double entropy(double[] entries) {
        int nbEntries = entries.length;
        HashMap<Double,Integer> occurrences = new HashMap<>();
        double key;
        for (int i=0; i<nbEntries; i++) {
            key = entries[i];
            if (occurrences.get(key) == null) {
                occurrences.put(key, 1);
            } else {
                int value = occurrences.get(key);
                occurrences.replace(key, value, value + 1);
            }
        }
        double entropy = 0.0;
        for (int value : occurrences.values()) {
            entropy -= value/nbEntries * Math.log(value/nbEntries) / Math.log(2);
        }
        return entropy;
    }

    // Calculate entropy
    public static double entropy(ArrayList<Object> entries) {
        int nbEntries = entries.size();
        HashMap<Object,Integer> occurrences = new HashMap<>();
        Object key;
        for (int i=0; i<nbEntries; i++) {
            key = entries.get(i);
            if (occurrences.get(key) == null) {
                occurrences.put(key, 1);
            } else {
                int value = occurrences.get(key);
                occurrences.replace(key, value, value + 1);
            }
        }
        double entropy = 0.0;
        for (int value : occurrences.values()) {
            entropy -= value/nbEntries * Math.log(value/nbEntries) / Math.log(2);
        }
        return entropy;
    }


}
