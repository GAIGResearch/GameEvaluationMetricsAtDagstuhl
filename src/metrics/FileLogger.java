package metrics;

import metrics.plot.MetricVisualiser;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;
import java.lang.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileLogger implements GameLogger{

    ArrayList<Integer> actionList;
    ArrayList<GameEvent[]> gameEvents;
    ArrayList<Double> decisivenessHistory;
    ArrayList<Map<String, Integer>> gameObjects;


    ArrayList<Double> scoreHistory;

    ArrayList<Double> scores = new ArrayList();

    MetricVisualiser visualiser = new MetricVisualiser();

    public GameLogger logEvents(GameEvent[] gameEventsNow)
    {
        gameEvents.add(gameEventsNow);
        return this;
    }
    
    @Override
    public GameLogger startGame() {
        actionList = new ArrayList<>();
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger terminateGame() {
        double normalisedEntropy = metrics.Utils.normalisedEntropy(actionList);
        System.out.println("[LOGGER] Entropy of actions: " + normalisedEntropy);

        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger logState(LoggableGameState state) {
        return null;
    }

    public void resetRecords() {
        actionList = new ArrayList<>();
        scoreHistory = new ArrayList<>();
        gameEvents = new ArrayList<>();
        decisivenessHistory = new ArrayList<>();
    }
}
