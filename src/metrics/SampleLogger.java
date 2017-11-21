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
    ArrayList<Double> decisivenessHistory;


    ArrayList<Double> scoreHistory;

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
    public GameLogger logScore(LoggableGameState state, double[] scores, GameEvent[] events) {
        scoreHistory.add(scores[0]);
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger startGame() {
        resetRecords();
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameLogger terminateGame() {


        /// PRINTING ENTROPY
        double entropy = metrics.Utils.entropy(actionList);
        Utils.printLogMsg("Entropy of actions: " + entropy);

        double[] scoreDiff = metrics.Utils.differentialArray(scoreHistory);
        Utils.printLogMsgWithTag("Score changes per game tick: ", scoreDiff);
        
        System.out.print("Decisiveness: ");
        for(double dec: decisivenessHistory){
            System.out.print(dec + ", ");
        }
        System.out.print("\n");



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

    public void resetRecords() {
        actionList = new ArrayList<>();
        scoreHistory = new ArrayList<>();
        gameEvents = new ArrayList<>();
        decisivenessHistory = new ArrayList<>();
    }

    @Override
    public GameLogger logAgentData(LoggableGameState state, AgentData agentData) {
        decisivenessHistory.add(agentData.getDecisiveness());
        return this;
    }


}
