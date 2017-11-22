package metrics;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class DataWriter {
    public String filenamePrefix = "";
    public String filename = "measures.log";

    public DataWriter(String filenamePrefix) {
        this.filenamePrefix = filenamePrefix;
    }

    public DataWriter() {

    }

    public String dataToString(Map<String, ArrayList<Double>> data, Map<String,Integer> groupsMap) {
        String outputStr = "";
        String measureNames = "keys = { ";

        for (Map.Entry<String,  ArrayList<Double>> entry : data.entrySet()) {
            String key = entry.getKey();
            measureNames += "\"" + key + "\" ";
            String entryStr = key + ".groupId = " + groupsMap.get(key) + ";\n";  //todo
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
        System.out.println("[DEBUG] String of measures to save\n" + outputStr);
        return outputStr;
    }

    public void printData(Map<String, ArrayList<Double>> data, Map<String,Integer> groupsMap) {
        System.out.println(dataToString(data, groupsMap));
    }

    public void writeDataToFile(Map<String, ArrayList<Double>> data, Map<String,Integer> groupsMap) {
        writeStrToFile(dataToString(data, groupsMap));
        System.out.println("Measure logs saved at " + filename);
    }

    public void writeStrToFile(String str) {
        try ( PrintWriter out = new PrintWriter(filename) ){
            out.println(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
