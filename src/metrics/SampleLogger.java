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

    Map<String,ArrayList<Double>> measures = new HashMap<>();
    ArrayList<Map<String,ArrayList<Double>>> gameLogs = new ArrayList<>();

    MetricVisualiser visualiser;

    String scoreField = "Score";
    String actionField = "Action";
    String decField = "Decisiveness";
    String convField = "Convergence";
    String outcomeScField = "OutcomeUncertaintyScore";
    String outcomeStField = "OutcomeUncertaintyState";

    DataWriter dataWriter = new DataWriter();


    public SampleLogger() {}

    @Override
    public GameLogger logState(LoggableGameState state) {

        measures.get(scoreField).add(state.getScore());
        measures.get(actionField).add((double) state.allActions()[0]);
        measures.get(decField).add(state.getDecisiveness());
        measures.get(convField).add(state.getConvergence());
        measures.get(outcomeStField).add(state.getOutcomeUncertaintyState());
        measures.get(outcomeScField).add(state.getOutcomeUncertaintyScore());

        // CODE IN PROGRESS:
//        for(GameEvent ge : state.getGameEvents())
//        {
//            if(!measures.containsKey(ge.name))
//                measures.put(ge.name, new ArrayList<>());
//            measures.get(ge.name).add(ge.time);
//        }

        if (state != null){
            visualiser.update(state);
        }

        return this;
    }

    @Override
    public GameLogger startGame() {

        measures = new HashMap<>();
        measures.put(scoreField, new ArrayList<>());
        measures.put(actionField, new ArrayList<>());
        measures.put(decField, new ArrayList<>());
        measures.put(convField, new ArrayList<>());
        measures.put(outcomeScField, new ArrayList<>());
        measures.put(outcomeStField, new ArrayList<>());

        visualiser = new MetricVisualiser();

        return this;
    }

    @Override
    public GameLogger terminateGame() {

        // this code works but is a bit ugly
        // since we need to repeat it for everything that we log
        gameLogs.add(measures);
        if (measures == null || measures.isEmpty()) {
            System.err.println("[WARNING] \"Measures\" is empty or NULL.");
        }
        System.out.println("[DEBUG] Below print the measures");
        dataWriter.printData(measures);
        System.out.println("[DEBUG] Above print the measures");
        dataWriter.writeDataToFile(measures);
        System.out.println(gameLogs);

        return this;
    }


}
