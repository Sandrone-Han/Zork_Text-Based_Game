package org.uob.a2.events;

/**
 * Observer interface for receiving game events.
 */
public interface GameObserver {
    void onGameEvent(GameEvent event);
}
