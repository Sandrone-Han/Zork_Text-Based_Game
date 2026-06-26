package org.uob.a2.events;

import org.uob.a2.gameobjects.GameState;

/**
 * Represents an event that occurs during the game.
 *
 * <p>
 * A GameEvent stores the event type, a readable message, and the current
 * GameState when the event occurs.
 * </p>
 */
public class GameEvent {
    private final GameEventType type;
    private final String message;
    private final GameState gameState;

    public GameEvent(GameEventType type, String message, GameState gameState) {
        this.type = type;
        this.message = message;
        this.gameState = gameState;
    }

    public GameEventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public GameState getGameState() {
        return gameState;
    }
}