package metrics;


import java.util.HashMap;

public interface LoggableGameState {
    
    // LoggableGameState copy();

    // LoggableGameState next(int[] actions);

    int nActions();

    // Id of all actions
    int[] allActions();

    double getScore();

    boolean isTerminal();

    HashMap<String, Integer> getGameObjects();

    GameEvent[] getGameEvents();

    public double getOutcomeUncertaintyState();
    public double getOutcomeUncertaintyScore();
    public double getConvergence();
    public double getDecisiveness();

    // List<Object> getGameState();

}
