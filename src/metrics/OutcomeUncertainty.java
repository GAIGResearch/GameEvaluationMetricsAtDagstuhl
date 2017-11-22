/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import core.game.StateObservation;
import java.util.ArrayList;
import java.util.Random;
import static metrics.Utils.normalisedEntropy;
import ontology.Types;
import static tracks.singlePlayer.tools.ucbOptimizerAgent.Agent.ROLLOUT_DEPTH;

/**
 *
 * @author vv
 */
public class OutcomeUncertainty {
    private int depth;
    private int nRoll;
    private boolean score;
    private Random rnd;
    
    
    public OutcomeUncertainty(int depth, int nRoll, boolean score){
        this.depth = depth;
        this.nRoll = nRoll;
        this.score=score;
        this.rnd = new Random();
    }
    
    public double computeUncertainty(StateObservation stateObs){
        double[] results = new double[this.nRoll];
        ArrayList<Types.ACTIONS> actions = stateObs.getAvailableActions();
        StateObservation current = null;

        for(int i=0; i<this.nRoll; i++){
            current = stateObs.copy();
            while (!current.isGameOver() && this.depth >= ROLLOUT_DEPTH) {
                int action = this.rnd.nextInt(actions.size());
                current.advance(actions.get(action));
                depth++;
            }
            if(this.score){
                results[i] = current.getGameScore();
            }else{
                results[i] = stateObs.getGameWinner().ordinal();
            }
        }
        return normalisedEntropy(results);
    }

}
