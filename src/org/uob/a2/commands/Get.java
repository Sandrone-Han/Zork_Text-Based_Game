package org.uob.a2.commands;

import org.uob.a2.events.GameEvent;
import org.uob.a2.events.GameEventType;
import org.uob.a2.gameobjects.Equipment;
import org.uob.a2.gameobjects.GameState;
import org.uob.a2.gameobjects.Item;
import org.uob.a2.gameobjects.Player;
import org.uob.a2.gameobjects.Room;

public class Get extends Command {

    public Get(String item) {
        this.commandType = CommandType.GET;
        this.value = item;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        Room currentRoom = gameState.getMap().getCurrentRoom();

        if (currentRoom.hasEquipment(this.value)) {
            Equipment equipmentByName = currentRoom.getEquipmentByName(this.value);

            if (!player.hasEquipment(this.value)) {
                player.addEquipment(equipmentByName);
                currentRoom.getEquipments().remove(equipmentByName);
                gameState.addScore(10);

                gameState.getEventManager().notifyObservers(
                        new GameEvent(GameEventType.ITEM_PICKED_UP,
                                "Player picked up equipment: " + this.value,
                                gameState)
                );

                return "You pick up: " + this.value;
            } else {
                return "You already have " + this.value;
            }
        }

        if (currentRoom.hasItem(this.value)) {
            Item item = currentRoom.getItemByName(this.value);

            if (!player.hasItem(this.value)) {
                player.addItem(item);
                currentRoom.getItems().remove(item);
                gameState.addScore(10);

                gameState.getEventManager().notifyObservers(
                        new GameEvent(GameEventType.ITEM_PICKED_UP,
                                "Player picked up item: " + this.value,
                                gameState)
                );

                return "You pick up: " + this.value;
            } else {
                return "You already have " + this.value;
            }
        }

        return "No " + this.value + " to get.";
    }

    @Override
    public String toString() {
        return "Get Command: " + this.value;
    }
}
