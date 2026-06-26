package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the status command, allowing the player to retrieve information
 * about their inventory, specific items, or their overall status.
 *
 * <p>
 * The status command can display a list of items in the player's inventory, 
 * provide details about a specific item, or show the player's general status.
 * </p >
 */
public class Status extends Command {


    private Map gameMap;

    public Status(String target) {
        this.commandType = CommandType.STATUS;
        this.value = target;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        StringBuilder statusMessage = new StringBuilder();


        if (this.value == null || this.value.isEmpty() || this.value.equalsIgnoreCase("player")) {
            statusMessage.append("Player Status:\n");
            statusMessage.append("Name: ").append(player.getName()).append("\n");
            statusMessage.append("Score: ").append(gameState.totalScore()).append("\n");
            return statusMessage.toString();
        }


        if (this.value.equalsIgnoreCase("inventory")) {
            for (Item item : player.getInventory()) {
                if (!item.getHidden()) {
                    statusMessage.append("- ").append(item.getName())
                            .append(": ").append(item.getDescription()).append("\n");
                }
            }
            for (Equipment equipment : player.getEquipment()) {
                if (!equipment.getHidden()) {
                    statusMessage.append("- ").append(equipment.getName())
                            .append(": ").append(equipment.getDescription()).append("\n");
                }
            }
            return statusMessage.toString();
        }


        if (this.value.equalsIgnoreCase("map")) {
            gameMap.displayMap();

        }


        Item item = player.getItem(this.value);
        if (item != null && !item.getHidden()) {
            statusMessage.append(item.getDescription()).append("\n");
            return statusMessage.toString();
        }

        Equipment equipment = player.getEquipment(this.value);
        if (equipment != null && !equipment.getHidden()) {
            statusMessage.append(equipment.getDescription()).append("\n");
            return statusMessage.toString();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Status Command: " + (this.value != null ? this.value : "player");
    }

}