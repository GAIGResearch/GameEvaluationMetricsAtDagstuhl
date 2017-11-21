package tracks.gameDesign.logger;

import core.game.StateObservation;
import metrics.LoggableGameState;

public class GVGAILoggableGameState implements LoggableGameState {

    StateObservation stateObservation;

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
}
