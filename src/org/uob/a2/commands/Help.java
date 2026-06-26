package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

public class Help extends Command {
    public Help(String topic) {
        this.commandType = CommandType.HELP;
        this.value = topic;
    }

    @Override
    public String execute(GameState gameState) {
        if (value == null || value.isEmpty()) {
            return getGeneralHelp();
        } else if ("move".equals(value.toLowerCase())) {
            return getMoveHelp();
        } else if ("look".equals(value.toLowerCase())) {
            return getLookHelp();
        } else if ("get".equals(value.toLowerCase())) {
            return getGetHelp();
        } else if ("drop".equals(value.toLowerCase())) {
            return getDropHelp();
        } else if ("use".equals(value.toLowerCase())) {
            return getUseHelp();
        } else if ("status".equals(value.toLowerCase())) {
            return getStatusHelp();
        } else if ("help".equals(value.toLowerCase())) {
            return getGeneralHelp();
        } else if ("quit".equals(value.toLowerCase())) {
            return getQuitHelp();
        } else if ("combine".equals(value.toLowerCase())) {
            return getCombineHelp();
        } else {
            return "Invalid information。";
        }
    }


    private String getGeneralHelp() {
        return "Welcome to the game! Here are the available commands:\n" +
                "- MOVE: Move to a different location (e.g., 'move north').\n" +
                "- LOOK: Look around the current room or inspect an object.\n" +
                "- GET: Pick up an item from the current room (e.g., 'get key').\n" +
                "- DROP: Drop an item from your inventory (e.g., 'drop key').\n" +
                "- USE: Use an item in your inventory (e.g., 'use key' or 'use key on chest').\n" +
                "- STATUS: Check your current status, including inventory.(e.g. 'status player' or 'status inventory').\n" +
                "- HELP: Display this help information or get help on a specific topic (e.g., 'help move').\n" +
                "- QUIT: Exit the game.\n" +
                "- COMBINE: Combine two items to create something new (e.g., 'combine stick and rope').";
    }

    private String getMoveHelp() {
        return "- MOVE: Move to a different location (e.g., 'move north').";
    }

    private String getLookHelp() {
        return "- LOOK: Look around the current room or inspect an object.";
    }

    private String getGetHelp() {
        return "- GET: Pick up an item from the current room (e.g., 'get key').";
    }

    private String getDropHelp() {
        return "- DROP: Drop an item from your inventory (e.g., 'drop key').";
    }

    private String getUseHelp() {
        return "- USE: Use an item in your inventory (e.g., 'use key' or 'use key on chest').";
    }

    private String getStatusHelp() {
        return "- STATUS: Check your current status, including inventory.(e.g. 'status player' or 'status inventory').";
    }

    private String getQuitHelp() {
        return "- QUIT: Exit the game.";
    }

    private String getCombineHelp() {
        return "- COMBINE: Combine two items to create something new (e.g., 'combine stick and rope').";
    }


    @Override
    public String toString() {
        return "Help Command: " + value;
    }

}
