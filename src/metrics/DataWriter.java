package metrics;

import java.util.ArrayList;
import java.util.Map;

public class DataWriter {
    public String filenamePrefix = "";

    public DataWriter(String filenamePrefix) {
        this.filenamePrefix = filenamePrefix;
    }

    public DataWriter() {

    }

    public String dataToString(Map<String, ArrayList<Double>> data) {
        String outputStr = "";
        String measureNames = "keys = { ";

        for (Map.Entry<String,  ArrayList<Double>> entry : data.entrySet()) {
            String key = entry.getKey();
            measureNames += key + " ";
            String entryStr = key + ".groupId = " + 0 + ";\n";  //todo
            entryStr += key + ".data = [";
            ArrayList<Double> values = entry.getValue();
            for (Double value: values) {
                entryStr += value + " ";
            }
            entryStr += "];\n";
            outputStr += entryStr;
        }
        measureNames += "};\n";
        outputStr = measureNames + outputStr;
        return outputStr;
    }

    public void printData(Map<String, ArrayList<Double>> data) {
        System.out.println(dataToString(data));
    }
}
