package tracks.gameDesign.logger;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import metrics.GameEvent;
import metrics.GameLogger;
import metrics.SampleLogger;
import ontology.Types;
import tools.ElapsedCpuTimer;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

/**
 * Created by dperez on 21/11/2017.
 */
public class Agent extends AbstractPlayer {

    private tracks.singlePlayer.advanced.sampleMCTS.Agent actualAgent;

    private GameLogger logger;
    GVGAILoggableGameState gvgaiLoggableGameState;

    /**
     * initialize all variables for the agent
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     */
    public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){
        actualAgent = new tracks.singlePlayer.advanced.sampleMCTS.Agent(stateObs, elapsedTimer);
        logger = new SampleLogger();
        logger.startGame();
        gvgaiLoggableGameState = new GVGAILoggableGameState();
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

        /// LOGGING ACTIONS.
        gvgaiLoggableGameState.setGameState(stateObs);
        logger.logAction(gvgaiLoggableGameState, new int[]{a.ordinal()}, null);
        // double score = stateObs.getGameScore();
        // logger.logScore(null, new double[]{score}, null);

        /// LOGGING GAME EVENTS
        logEvents(stateObs);

        return a;
    }

    public void result(StateObservation stateObs, ElapsedCpuTimer elapsedCpuTimer)
    {
        /// LOGGING GAME EVENTS
        logEvents(stateObs);

        logger.terminateGame();
    }

    private void logEvents(StateObservation stateObs)
    {
        ArrayList<String> eventsThisTick = stateObs.getEventsThisTick();
        if(eventsThisTick != null) {
            GameEvent[] events = new GameEvent[eventsThisTick.size()];
            int i = 0;

            for (String event : eventsThisTick) {
//                GameEvent ge = new GameEvent(event);
                GameEvent ge = new GameEvent(event, stateObs.getGameTick(), stateObs.getAvatarPosition());
                events[i] = ge;
                i++;
            }

            logger.logEvents(events);
        }else if(stateObs.getGameTick() > 0){
            logger.logEvents(null);
        }
    }
}