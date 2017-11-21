package metrics;


public interface LoggableGameState {
    
    LoggableGameState copy();

    LoggableGameState next(int[] actions);

    int nActions();

    double getScore();

    boolean isTerminal();

    // List<Object> getGameState();

}
