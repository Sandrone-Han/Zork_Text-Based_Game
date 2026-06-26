package org.uob.a2.engine;

import java.util.ArrayList;
import java.util.Scanner;

import org.uob.a2.commands.Command;
import org.uob.a2.commands.CommandType;
import org.uob.a2.events.GameEvent;
import org.uob.a2.events.GameEventType;
import org.uob.a2.events.InventoryObserver;
import org.uob.a2.events.RoomObserver;
import org.uob.a2.events.ScoreObserver;
import org.uob.a2.gameobjects.GameState;
import org.uob.a2.parser.Parser;
import org.uob.a2.parser.Token;
import org.uob.a2.parser.Tokeniser;
import org.uob.a2.utils.GameStateFileParser;

/**
 * Controls the main loop of the text-based game engine.
 */
public class GameEngine {
    private GameState gameState;
    private final Scanner scanner;
    private final Parser parser;
    private final Tokeniser tokeniser;

    public GameEngine(String gameDataPath) {
        this.gameState = GameStateFileParser.parse(gameDataPath);
        this.scanner = new Scanner(System.in);
        this.parser = new Parser();
        this.tokeniser = new Tokeniser();

        registerDefaultObservers();
    }

    public void start() {
        printIntroduction();

        boolean running = true;

        while (running) {
            System.out.println(">> ");
            String input = scanner.nextLine();

            try {
                if (input.equalsIgnoreCase("map")) {
                    gameState.getMap().displayMap();
                    continue;
                }

                ArrayList<Token> tokens = tokeniser.tokenise(input);
                Command command = parser.parse(tokens);

                turn(command);

                if (command.getCommandType() == CommandType.QUIT) {
                    gameState.getEventManager().notifyObservers(
                            new GameEvent(GameEventType.GAME_QUIT, "Player quit the game.", gameState)
                    );

                    System.out.println("Signal interruption.......");
                    running = false;
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void turn(Command command) {
        String result = command.execute(gameState);
        System.out.println(result);
    }

    private void registerDefaultObservers() {
        gameState.getEventManager().addObserver(new ScoreObserver());
        gameState.getEventManager().addObserver(new RoomObserver());
        gameState.getEventManager().addObserver(new InventoryObserver());
    }

    private void printIntroduction() {
        System.out.println("You wake up after a loud bang,surrounded by white smoke," + "\n" +
                "shadowed by a door and you open it." + "\n" +
                "Fortunately you find a bag behind the door." + "\n" +
                "Now you need to seach this house............"
        );

        System.out.println("Type to start: ");
    }
}