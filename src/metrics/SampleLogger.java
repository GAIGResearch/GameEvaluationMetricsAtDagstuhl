/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import java.util.ArrayList;

import metrics.plot.MetricVisualiser;

/**
 *
 * @author vv
 */
public class SampleLogger implements GameLogger {

    ArrayList<Integer> actionList;

    ArrayList<Double> scores = new ArrayList();
    
    MetricVisualiser visualiser = new MetricVisualiser();
    
    @Override
    public GameLogger logAction(LoggableGameState state, int[] actions, GameEvent[] events) {
        actionList.add(actions[0]);
        if (state != null){
            scores.add(state.getScore());  
        }
        else{
        	scores.add(Math.random() * 10);
        }
        visualiser.update(scores);
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
//        MetricVisualiser visualiser = Met
        return this;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
