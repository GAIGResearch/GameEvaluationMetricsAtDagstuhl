/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author vv
 */
public class SampleLogger implements GameLogger {

    ArrayList<Integer> actionList;
    ArrayList<GameEvent[]> gameEvents;
    ArrayList<Map<String, Integer>> gameObjects;

    @Override
    public GameLogger logAction(LoggableGameState state, int[] actions, GameEvent[] events) {
        actionList.add(actions[0]);
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public GameLogger logEvents(GameEvent[] gameEventsNow)
    {
        gameEvents.add(gameEventsNow);
        return this;
    }

    public GameLogger logObjectDensity(Map<String, Integer> objects)
    {
        gameObjects.add(objects);
        return this;
    }

    @Override
    public GameLogger startGame() {
        actionList = new ArrayList<>();
        gameEvents = new ArrayList<>();
        gameObjects = new ArrayList<>();

        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger terminateGame() {

        debug();
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void debug()
    {
        /// PRINTING ENTROPY
        double entropy = metrics.Utils.entropy(actionList);
        System.out.println("[LOGGER] Entropy of actions: " + entropy);

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
                for(GameEvent ge : ges) System.out.print(ge.name + "; ");
                System.out.println();
            }
            timeSteps++;
        }

    }

}
