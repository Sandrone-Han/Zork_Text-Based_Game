package org.uob.a2.events;

/**
 * Observes room movement events.
 */
public class RoomObserver implements GameObserver {

    @Override
    public void onGameEvent(GameEvent event) {
        if (event.getType() == GameEventType.ROOM_CHANGED) {
            System.out.println("[Room Observer] " + event.getMessage());
        }
    }
}