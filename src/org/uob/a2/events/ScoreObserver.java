package org.uob.a2.events;

/**
 * Observes score changes.
 */
public class ScoreObserver implements GameObserver {

    @Override
    public void onGameEvent(GameEvent event) {
        if (event.getType() == GameEventType.SCORE_CHANGED) {
            System.out.println("[Score Observer] " + event.getMessage());
        }
    }
}