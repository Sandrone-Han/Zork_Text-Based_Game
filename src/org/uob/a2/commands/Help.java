package org.uob.a2.commands;

import org.uob.a2.gameobjects.GameState;

/** Displays general or command-specific help. */
public class Help extends Command {
    public Help(String topic) {
        this.commandType = CommandType.HELP;
        this.value = topic;
    }

    @Override
    public String execute(GameState gameState) {
        if (value == null || value.isBlank()) {
            return getGeneralHelp();
        }

        return switch (value.toLowerCase()) {
            case "move" -> "MOVE Command: Use the 'move' command followed by a direction (e.g., 'move north').";
            case "look" -> "LOOK Command: Look around or inspect an object (e.g., 'look chest').";
            case "get" -> "GET Command: Pick up an item in the room (e.g., 'get key').";
            case "drop" -> "DROP Command: Drop an item from your inventory (e.g., 'drop key').";
            case "use" -> "USE Command: Use equipment on a target (e.g., 'use key on chest').";
            case "status" -> "STATUS Command: Show the player, inventory, or an object's status.";
            case "help" -> getGeneralHelp();
            case "quit" -> "QUIT Command: Exit the game and display the player's final status.";
            case "combine" -> "COMBINE Command: Combine two carried items (e.g., 'combine stick with cloth').";
            default -> "No help available for the topic: " + value;
        };
    }

    private String getGeneralHelp() {
        return "Welcome to the game! Here are the available commands:\n" +
                "- MOVE: Move to a different location (e.g., 'move north').\n" +
                "- LOOK: Look around the current room or inspect an object.\n" +
                "- GET: Pick up an item from the current room (e.g., 'get key').\n" +
                "- DROP: Drop an item from your inventory (e.g., 'drop key').\n" +
                "- USE: Use equipment on a target (e.g., 'use key on chest').\n" +
                "- STATUS: Check the player or inventory status.\n" +
                "- HELP: Display general or command-specific help.\n" +
                "- QUIT: Exit the game.\n" +
                "- COMBINE: Combine two carried items.";
    }

    @Override
    public String toString() {
        return "HELP Command: " + value;
    }
}
