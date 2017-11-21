/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author vv
 */
public class SampleLogger implements GameLogger {

    ArrayList<Integer> actionList;
    ArrayList<GameEvent[]> gameEvents;


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

    @Override
    public GameLogger startGame() {
        actionList = new ArrayList<>();
        gameEvents = new ArrayList<>();
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger terminateGame() {


        /// PRINTING ENTROPY
        double entropy = metrics.Utils.entropy(actionList);
        System.out.println("[LOGGER] Entropy of actions: " + entropy);


        /// PRINTING GAME EVENTS
        int timeSteps = 0;
        for(GameEvent[] ges : gameEvents)
        {
            if(ges != null)
            {
                System.out.print("[LOGGER] " + timeSteps + " ");
                for(GameEvent ge : ges) System.out.print(ge.name + ";");
                System.out.println();
            }
            timeSteps++;
        }

        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
