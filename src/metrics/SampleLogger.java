/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import java.util.*;

import metrics.plot.MetricVisualiser;

/**
 *
 * @author vv
 */
public class SampleLogger implements GameLogger {

    ArrayList<Integer> actionList;
//    HashMap<Integer, Integer> actionMap;

    ArrayList<GameEvent[]> gameEvents;
    ArrayList<Double> decisivenessHistory;
    ArrayList<Double> convergenceHistory;
    ArrayList<Map<String, Integer>> gameObjects;
    // ArrayList<Double> scoreHistory = new ArrayList();

    Map<String,ArrayList<Double>> measures = new HashMap<>();

    ArrayList<Map<String,ArrayList<Double>>> gameLogs = new ArrayList<>();




    // ArrayList<ArrayList<Double>> scoreHistories = new ArrayList<>();
    ArrayList<Double> scoreHistory;

    MetricVisualiser visualiser;
//    MetricVisualiser visualiserForAction = new MetricVisualiser();

    String scoreField = "Score";


    public SampleLogger() {
        // need to build the things that we wish to log
        // good to do it this way using a HashMap because we need to process them in general ways later

        // start with the score
    }

    @Override
    public GameLogger logState(LoggableGameState state) {

        actionList.add(state.allActions()[0]);
        gameEvents.add(state.getGameEvents());
        gameObjects.add(state.getGameObjects());

        measures.get(scoreField).add(state.getScore());

        if (state != null){
            visualiser.update(state);
        }

        return this;
    }

    @Override
    public GameLogger startGame() {

        measures = new HashMap<>();
        measures.put(scoreField, new ArrayList<>());


        actionList = new ArrayList<>();
        gameEvents = new ArrayList<>();
        gameObjects = new ArrayList<>();
        visualiser = new MetricVisualiser();

        resetRecords();
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger terminateGame() {

        // this code works but is a bit ugly
        // since we need to repeat it for everything that we log
        gameLogs.add(measures);

        System.out.println(gameLogs);

        // scoreHistories.add(scoreHistory);

        //debug();
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void debug()
    {
        /// PRINTING ENTROPY
        double normalisedEntropy = metrics.Utils.normalisedEntropy(actionList);
        System.out.println("[LOGGER] Entropy of actions: " + normalisedEntropy);
//        MetricVisualiser visualiser = Met

        Utils.printLogMsg("Entropy of actions: " + normalisedEntropy);

        double[] scoreDiff = metrics.Utils.differentialArray(scoreHistory);
        Utils.printLogMsgWithTag("Score changes per game tick: ", scoreDiff);
        
        System.out.print("Decisiveness: ");
        for(double dec: decisivenessHistory){
            System.out.print(dec + ", ");
        }
        System.out.print("\n");
        
        System.out.print("Convergence: ");
        for(double dec: convergenceHistory){
            System.out.print(dec + ", ");
        }
        System.out.print("\n");


        /// PRINTING GAME OBJECTS
        //ArrayList<Map<String, Integer>> gameObjects;
        int timeSteps = 0;
        for(Map<String, Integer> gObjs : gameObjects)
        {
            if(gObjs != null)
            {
                System.out.print("[LOGGER] " + timeSteps + " ");
                Iterator<Map.Entry<String, Integer>> objsIt = gObjs.entrySet().iterator();
                while(objsIt.hasNext())
                {
                    Map.Entry<String, Integer> entry = objsIt.next();
                    System.out.print(entry.getKey() + ": " + entry.getValue() + "; ");
                }
                System.out.println();

            }
            timeSteps++;
        }

        /// PRINTING GAME EVENTS
        timeSteps = 0;
        for(GameEvent[] ges : gameEvents)
        {
            if(ges != null)
            {
                System.out.print("[LOGGER] " + timeSteps + " ");
                for(GameEvent ge : ges) System.out.print(ge.name + " at position " + ge.avatarPosition + "; ");
                System.out.println();
            }
            timeSteps++;
        }

        System.out.println();
        System.out.println("Score frequency distribution");
        System.out.println(new FrequencyMap().add(scoreDiff).getMap());

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        System.out.println();
    }

    public void resetRecords() {
        actionList = new ArrayList<>();
        scoreHistory = new ArrayList<>();
        gameEvents = new ArrayList<>();
        gameObjects = new ArrayList<>();
        decisivenessHistory = new ArrayList<>();
        convergenceHistory = new ArrayList<>();
    }

}
