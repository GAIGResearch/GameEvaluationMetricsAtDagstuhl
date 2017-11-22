package tracks.gameDesign.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import metrics.GameEvent;
import metrics.GameLogger;
import metrics.SampleLogger;
import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import core.vgdl.VGDLRegistry;
import metrics.OutcomeUncertainty;



/**
 * Created by dperez on 21/11/2017.
 */
public class Agent extends AbstractPlayer {

    private tracks.singlePlayer.advanced.sampleMCTS.Agent actualAgent;
    private OutcomeUncertainty outcomeUncertainty;

    GVGAILoggableGameState gvgaiLoggableGameState;

    /**
     * initialize all variables for the agent
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     */
    public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){
        actualAgent = new tracks.singlePlayer.advanced.sampleMCTS.Agent(stateObs, elapsedTimer);
        gvgaiLoggableGameState = new GVGAILoggableGameState();
        outcomeUncertainty = new OutcomeUncertainty(15, 20);
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

        // LOGGING OBJECT DENSITIY
        HashMap<String, Integer> thisCountMap = logObjectDensity(stateObs);
        gvgaiLoggableGameState.setGameObjects(thisCountMap);

        /// LOGGING GAME EVENTS

        GameEvent events[] = logEvents(stateObs);
        gvgaiLoggableGameState.setGameEvents(events);


        gvgaiLoggableGameState.setDecisiveness(actualAgent.getDecisiveness());
        gvgaiLoggableGameState.setConvergence(actualAgent.getConvergence());
        gvgaiLoggableGameState.setOutcomeUncertaintyScore(outcomeUncertainty.computeUncertainty(stateObs, true));
        gvgaiLoggableGameState.setOutcomeUncertaintyState(outcomeUncertainty.computeUncertainty(stateObs, false));

        gvgaiLoggableGameState.setGameState(stateObs);
        gvgaiLoggableGameState.setActions(new int[]{a.ordinal()});

        logger.logState(gvgaiLoggableGameState);

        //logger.logAction(gvgaiLoggableGameState, null, null);
        // double score = stateObs.getGameScore();
        // logger.logScore(null, new double[]{score}, null);

        return a;
    }
    

    public void result(StateObservation stateObs, ElapsedCpuTimer elapsedCpuTimer)
    {
        /// LOGGING GAME EVENTS
        logEvents(stateObs);
    }

    private GameEvent[] logEvents(StateObservation stateObs)
    {
        ArrayList<String> eventsThisTick = stateObs.getEventsThisTick();
        if(eventsThisTick != null) {
            GameEvent[] events = new GameEvent[eventsThisTick.size()];
            int i = 0;

            for (String event : eventsThisTick) {
                GameEvent ge = new GameEvent(event, stateObs.getGameTick(), stateObs.getAvatarPosition());
                events[i] = ge;
                i++;
            }

            return events;
        //}else if(stateObs.getGameTick() > 0){
        }
        return null;
    }

    ///LOG OBJECT DENSITY
    private HashMap<String, Integer> logObjectDensity(StateObservation stateObs)
    {
        ArrayList<Observation>[] npcPositions = stateObs.getNPCPositions();
        HashMap<String, Integer> thisCountMap = new HashMap<String, Integer>();

        _logObjects(thisCountMap, stateObs.getNPCPositions());
        _logObjects(thisCountMap, stateObs.getImmovablePositions());
        _logObjects(thisCountMap, stateObs.getMovablePositions());
        _logObjects(thisCountMap, stateObs.getResourcesPositions());
        _logObjects(thisCountMap, stateObs.getPortalsPositions());
        _logObjects(thisCountMap, stateObs.getFromAvatarSpritesPositions());

        //logger.logObjectDensity(thisCountMap);
        return thisCountMap;

    }

    private void _logObjects(Map<String, Integer> thisCountMap, ArrayList<Observation>[] observations)
    {
        if(observations!=null) for (ArrayList<Observation> obs : observations) {
            int count = obs.size();
            if (count > 0) {
                int objtype = obs.get(0).itype;
                String objStr = VGDLRegistry.GetInstance().getRegisteredSpriteKey(objtype);

                thisCountMap.put(objStr, count);
            }
        }
    }



}