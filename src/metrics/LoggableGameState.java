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

    // List<Object> getGameState();

}
