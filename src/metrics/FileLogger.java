package metrics;

import java.nio.*;
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

    private ArrayList<Integer> actionList;
    private ArrayList<GameEvent[]> gameEvents;
    private ArrayList<Double> scoreHistory;

    public GameLogger logEvents(GameEvent[] gameEventsNow)
    {
        gameEvents.add(gameEventsNow);
        return this;
    }

    @Override
    public GameLogger logObjectDensity(Map<String, Integer> objects) {
        return null;
    }

    @Override
    public GameLogger logAction(LoggableGameState state, int[] actions, GameEvent[] events) {
        actionList.add(actions[0]);

        Charset utf8 = StandardCharsets.UTF_8;
        //List<String> logg = new ArrayList<String>();
        List<String> logg = new ArrayList<>();

        actionList.forEach((action)->logg.add(action.toString()));

        try {
            Files.write(Paths.get("Logg_file.txt"), logg, utf8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger startGame() {
        actionList = new ArrayList<>();
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger terminateGame() {
        double entropy = metrics.Utils.entropy(actionList);
        System.out.println("[LOGGER] Entropy of actions: " + entropy);
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void resetRecords() {
        actionList = new ArrayList<>();
        scoreHistory = new ArrayList<>();
        gameEvents = new ArrayList<>();
    }
}
