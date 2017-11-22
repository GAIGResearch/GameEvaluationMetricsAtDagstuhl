/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import java.util.*;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
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

    int gameTick = 0;
    ArrayList<String> eventsThisGame;
    ArrayList<String> objectsThisGame;


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

        // Manage events
        manageEvents(state);

        // Manage game objects:
        manageObjects(state);

        if (state != null){
            visualiser.update(state);
        }
        gameTick++;

        return this;
    }

    private void manageEvents(LoggableGameState state)
    {
        ArrayList<String> eventsThisTick = new ArrayList<>();
        if(state.getGameEvents() != null) for(GameEvent ge : state.getGameEvents())
        {
            //Add 0.0 up to this point if it's new, and 0.0 for this tick
            if(!measures.containsKey(ge.name))
            {
                measures.put(ge.name, new ArrayList<>());
                for(int i = 0; i <= gameTick; ++i)
                    measures.get(ge.name).add(0.0);
            }else{
                measures.get(ge.name).add(0.0);
            }

            //Keep a log of all events in the game.
            if(!eventsThisGame.contains(ge.name))
            {
                eventsThisGame.add(ge.name);
            }

            //Update the events occurrences:
            double howManyThisTick = measures.get(ge.name).get(gameTick);
            measures.get(ge.name).set(gameTick, howManyThisTick + 1.0);

            //Track of the events of this tick
            eventsThisTick.add(ge.name);
        }

        //Need to update those known events that didn't happen this tick.
        for(String name : eventsThisGame)
        {
            if(!eventsThisTick.contains(name))
                measures.get(name).add(0.0);
        }
    }


    private void manageObjects(LoggableGameState state)
    {
        ArrayList<String> objectsThisTick = new ArrayList<>();

        HashMap<String, Integer> gameObjects = state.getGameObjects();

        Iterator<Map.Entry<String, Integer>> itObjects = gameObjects.entrySet().iterator();

        while(itObjects.hasNext())
        {
            Map.Entry<String, Integer> objectOcc = itObjects.next();

            if(!measures.containsKey(objectOcc.getKey()))
            {
                measures.put(objectOcc.getKey(), new ArrayList<>());
                for(int i = 0; i <= gameTick; ++i)
                    measures.get(objectOcc.getKey()).add(0.0);
            }

            //Log of all objects in this game
            if(!objectsThisGame.contains(objectOcc.getKey()))
            {
                objectsThisGame.add(objectOcc.getKey());
            }

            measures.get(objectOcc.getKey()).add((double) objectOcc.getValue());

            objectsThisTick.add(objectOcc.getKey());
        }

        //Need to update those known objects that didn't appear on this tick.
        for(String name : objectsThisGame)
        {
            if(!objectsThisTick.contains(name))
                measures.get(name).add(0.0);
        }
    }


    @Override
    public GameLogger startGame() {

        gameTick = 0;
        measures = new HashMap<>();
        measures.put(scoreField, new ArrayList<>());
        measures.put(actionField, new ArrayList<>());
        measures.put(decField, new ArrayList<>());
        measures.put(convField, new ArrayList<>());
        measures.put(outcomeScField, new ArrayList<>());
        measures.put(outcomeStField, new ArrayList<>());

        eventsThisGame = new ArrayList<>();
        objectsThisGame = new ArrayList<>();
        visualiser = new MetricVisualiser();

        return this;
    }

    @Override
    public GameLogger terminateGame() {

        // this code works but is a bit ugly
        // since we need to repeat it for everything that we log
        gameLogs.add(measures);
        if (measures == null || measures.isEmpty()) {
            System.err.println("[WARNING] Measures is empty or null.");
        }
        System.out.println("[DEBUG] Below print the measures");
        dataWriter.printData(measures);
        System.out.println("[DEBUG] Above print the measures");

        System.out.println(gameLogs);

        return this;
    }


}
