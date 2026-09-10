package org.uob.a2.utils;

/** Thrown when a game data file is missing, malformed, or internally inconsistent. */
public class GameDataException extends RuntimeException {
    public GameDataException(String message) {
        super(message);
    }

    public GameDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
