package tracks.gameDesign.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import metrics.AgentData;
import metrics.GameEvent;
import metrics.GameLogger;
import metrics.SampleLogger;
import ontology.Types;
import tools.ElapsedCpuTimer;
import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import core.vgdl.VGDLRegistry;
import java.util.Random;
import static metrics.Utils.normalisedEntropy;
import ontology.Types.ACTIONS;
import static tracks.singlePlayer.tools.ucbOptimizerAgent.Agent.ROLLOUT_DEPTH;


/**
 * Created by dperez on 21/11/2017.
 */
public class Agent extends AbstractPlayer {

    private tracks.singlePlayer.advanced.sampleMCTS.Agent actualAgent;

    private GameLogger logger;
    GVGAILoggableGameState gvgaiLoggableGameState;
    public Random rnd;

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
        rnd = new Random();
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

        /// LOGGING OBJECT DENSITIY
        HashMap<String, Integer> thisCountMap = logObjectDensity(stateObs);
        gvgaiLoggableGameState.setGameObjects(thisCountMap);

        /// LOGGING GAME EVENTS
        logEvents(stateObs);

        /// LOGGING ACTIONS.
        AgentData agentData = new AgentData();
        agentData.setDecisiveness(actualAgent.getDecisiveness());
        agentData.setConvergence(actualAgent.getConvergence());
        logger.logAgentData(null, agentData);


        gvgaiLoggableGameState.setGameState(stateObs);
        logger.logAction(gvgaiLoggableGameState, new int[]{a.ordinal()}, null);
        System.out.println(scoreUncertainty(stateObs));
        System.out.println(outcomeUncertainty(stateObs));
        // double score = stateObs.getGameScore();
        // logger.logScore(null, new double[]{score}, null);

        return a;
    }
    
    public double scoreUncertainty(StateObservation stateObs){
        int depth = 15;
        int noRoll = 20;
        double[] scores = new double[noRoll];
        ArrayList<ACTIONS> actions = stateObs.getAvailableActions();
        StateObservation current = null;

        for(int i=0; i<noRoll; i++){
            current = stateObs.copy();
            while (!stateObs.isGameOver() && depth >= ROLLOUT_DEPTH) {
                int action = rnd.nextInt(actions.size());
                stateObs.advance(actions.get(action));
                depth++;
            }
            scores[i] = stateObs.getGameScore();
        }
        return normalisedEntropy(scores);
    }
    
        public double outcomeUncertainty(StateObservation stateObs){
        int depth = 15;
        int noRoll = 20;
        double[] outcomes = new double[noRoll];
        ArrayList<ACTIONS> actions = stateObs.getAvailableActions();
        StateObservation current = null;

        for(int i=0; i<noRoll; i++){
            current = stateObs.copy();
            while (!stateObs.isGameOver() && depth >= ROLLOUT_DEPTH) {
                int action = rnd.nextInt(actions.size());
                stateObs.advance(actions.get(action));
                depth++;
            }
            //TODO: check different outcome possibilites
            outcomes[i] = stateObs.getGameWinner().ordinal();
            System.out.println(outcomes[i]);
        }
        return normalisedEntropy(outcomes);
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
                GameEvent ge = new GameEvent(event, stateObs.getGameTick(), stateObs.getAvatarPosition());
                events[i] = ge;
                i++;
            }

            logger.logEvents(events);
        }else if(stateObs.getGameTick() > 0){
            logger.logEvents(null);
        }
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

        logger.logObjectDensity(thisCountMap);
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