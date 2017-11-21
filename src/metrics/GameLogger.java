package metrics;


/**
 *  Simple interface for recording gameplay logs.
 *  Different methods should probably be in different
 *  */
public interface GameLogger {


    GameLogger startGame();

    GameLogger terminateGame();

    GameLogger logAction(LoggableGameState state,
                          int[] actions,
                          GameEvent[] events);

//    public void logAction(AbstractGameState state,
//                          InputEvent[] actions,
//                          GameEvent[] events);

    

}
