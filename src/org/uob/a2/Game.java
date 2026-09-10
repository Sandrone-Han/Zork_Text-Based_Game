package org.uob.a2;

import org.uob.a2.engine.GameEngine;

/**
 * Entry point of the application.
 *
 * <p>
 * This class only starts the game engine. The game loop and execution logic are
 * handled by {@link GameEngine}.
 * </p>
 */
public class Game {
    public static void main(String[] args) {
        String gameDataPath = args.length > 0 ? args[0] : "data/game.json";
        GameEngine engine = new GameEngine(gameDataPath);
        engine.start();
    }
}

