package metrics;


public interface LoggableGameState {
    
    LoggableGameState copy();

    LoggableGameState next(int[] actions);

    int nActions();

    // Id of all actions
    int[] allActions();

    double getScore();

    boolean isTerminal();

    // List<Object> getGameState();

}
