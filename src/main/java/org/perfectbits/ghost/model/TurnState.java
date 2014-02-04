package org.perfectbits.ghost.model;

public class TurnState {

    private boolean initial;

    void setInitial(boolean b) {
       initial = false;
    }

    public enum Phase {

        YIN, YANG;
    }

    public enum Step {

        GHOSTS_MOVING, NEW_GHOSTS_ENTERS, PLAYER_MOVES, PLAYER_ACTION, BUDDA_PLACEMENT;
    }
    private Phase currentPhase = Phase.YIN;
    private Step currentStep = Step.GHOSTS_MOVING;

    public TurnState() {
        this.initial = true;
    }

    public TurnState(Phase currentPhase, Step currentStep) {
        super();
        this.currentPhase = currentPhase;
        this.currentStep = currentStep;
        this.initial = false;
    }

    boolean isInitial() {
        return initial;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public Step getCurrentStep() {
        return currentStep;
    }

    public boolean hasMoreSteps() {
        return nextStep() != null;
    }

    public TurnState nextStep() {
        switch (currentStep) {
            case GHOSTS_MOVING:
                return new TurnState(currentPhase, Step.NEW_GHOSTS_ENTERS);
            case NEW_GHOSTS_ENTERS:
                return new TurnState(Phase.YANG, Step.PLAYER_MOVES);
            case PLAYER_MOVES:
                return new TurnState(currentPhase, Step.PLAYER_ACTION);
            case PLAYER_ACTION:
                return new TurnState(currentPhase, Step.BUDDA_PLACEMENT);
            case BUDDA_PLACEMENT:
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "TurnState{" + "initial=" + initial + ", currentPhase=" + currentPhase + ", currentStep=" + currentStep + '}';
    }
    
}
