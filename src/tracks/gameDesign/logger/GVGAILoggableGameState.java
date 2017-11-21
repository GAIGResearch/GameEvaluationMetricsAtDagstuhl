package tracks.gameDesign.logger;

import java.util.HashMap;
import java.util.Map;

import metrics.LoggableGameState;
import core.game.StateObservation;

public class GVGAILoggableGameState implements LoggableGameState {

    StateObservation stateObservation;
    HashMap<String, Integer> gameObjects;

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
        return new int[0];
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

    public void setGameObjects(HashMap<String, Integer> gameObjects)
    {
        this.gameObjects = gameObjects;
    }

}
