package tracks.gameDesign.logger;

import java.util.HashMap;
import java.util.Map;

import metrics.GameEvent;
import metrics.LoggableGameState;
import core.game.StateObservation;

public class GVGAILoggableGameState implements LoggableGameState {

    StateObservation stateObservation;
    HashMap<String, Integer> gameObjects;
    GameEvent[] gameEvents;
    int[] actions;

    private double decisiveness;




    LoggableGameState setGameState(StateObservation stateObservation) {
        this.stateObservation = stateObservation;
        return this;
    }

    @Override
    public int nActions() {
        return stateObservation.getAvailableActions().size();
    }

    @Override
    public int[] allActions() {
        return actions;
    }

    @Override
    public double getScore() {
        return stateObservation.getGameScore();
    }

    @Override
    public boolean isTerminal() {
        return stateObservation.isGameOver();
    }

    @Override
    public HashMap<String, Integer> getGameObjects() {
        return this.gameObjects;
    }

    public GameEvent[] getGameEvents() {return this.gameEvents;}


    public double getDecisiveness() {
        return decisiveness;
    }

    public void setDecisiveness(double decisiveness) {
        this.decisiveness = decisiveness;
    }


    public void setGameObjects(HashMap<String, Integer> gameObjects)
    {
        this.gameObjects = gameObjects;
    }

    public void setGameEvents(GameEvent[] events)
    {
        this.gameEvents = events;
    }

    public void setActions(int[] actions)
    {
        this.actions = actions;
    }

}
