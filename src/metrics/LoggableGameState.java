package metrics;


import java.util.Map;

public interface LoggableGameState {
    
    // LoggableGameState copy();

    // LoggableGameState next(int[] actions);

    int nActions();

    // Id of all actions
    int[] allActions();

    double getScore();

    boolean isTerminal();

    Map<String, Integer> getGameObjects();

    // List<Object> getGameState();

}
