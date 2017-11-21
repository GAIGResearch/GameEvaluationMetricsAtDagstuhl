package tracks.gameDesign.logger;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import metrics.GameLogger;
import metrics.SampleLogger;
import ontology.Types;
import tools.ElapsedCpuTimer;

/**
 * Created by dperez on 21/11/2017.
 */
public class Agent extends AbstractPlayer {


    private tracks.singlePlayer.advanced.sampleMCTS.Agent actualAgent;

    private GameLogger logger;

    /**
     * initialize all variables for the agent
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     */
    public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){
        actualAgent = new tracks.singlePlayer.advanced.sampleMCTS.Agent(stateObs, elapsedTimer);
        logger = new SampleLogger();
        logger.startGame();
    }

    /**
     * return ACTION_NIL on every call to simulate doNothing player
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     * @return 	ACTION_NIL all the time
     */
    @Override
    public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
        Types.ACTIONS a = actualAgent.act(stateObs, elapsedTimer);
        logger.logAction(null, new int[]{a.ordinal()}, null);
        double score = stateObs.getGameScore();
        logger.logScore(null, new double[]{score}, null);
        return a;
    }

    public void result(StateObservation stateObs, ElapsedCpuTimer elapsedCpuTimer)
    {
        logger.terminateGame();
    }
}