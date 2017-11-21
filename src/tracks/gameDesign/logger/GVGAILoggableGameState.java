package tracks.gameDesign.logger;

import core.game.StateObservation;
import metrics.LoggableGameState;

import java.util.Map;

public class GVGAILoggableGameState implements LoggableGameState {

    StateObservation stateObservation;
    Map<String, Integer> gameObjects;

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
    public Map<String, Integer> getGameObjects() {
        return this.gameObjects;
    }

    public void setGameObjects(Map<String, Integer> gameObjects)
    {
        this.gameObjects = gameObjects;
    }

}
