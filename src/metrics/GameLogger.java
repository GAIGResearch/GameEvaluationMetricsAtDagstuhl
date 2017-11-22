package metrics;


import java.util.Map;

/**
 *  Simple interface for recording gameplay logs.
 *  Different methods should probably be in different
 *  */
public interface GameLogger {


    GameLogger startGame();

    GameLogger terminateGame();

    //GameLogger logEvents(GameEvent[] gameEventsNow);

    
    //GameLogger logAgentData(LoggableGameState state, AgentState agentData);

    //GameLogger logObjectDensity(Map<String, Integer> objects);

//    public void logAction(AbstractGameState state,
//                          InputEvent[] actions,
//                          GameEvent[] events);

    GameLogger logState(LoggableGameState state);


}
