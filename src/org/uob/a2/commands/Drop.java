package org.uob.a2.commands;

import org.uob.a2.events.GameEvent;
import org.uob.a2.events.GameEventType;
import org.uob.a2.gameobjects.Equipment;
import org.uob.a2.gameobjects.GameState;
import org.uob.a2.gameobjects.Item;
import org.uob.a2.gameobjects.Player;
import org.uob.a2.gameobjects.Room;

public class Drop extends Command {

    public Drop(String item) {
        this.commandType = CommandType.DROP;
        this.value = item;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        Room currentRoom = gameState.getMap().getCurrentRoom();

        Equipment equipment = player.getEquipment(value);
        if (equipment != null) {
            player.getEquipment().remove(equipment);
            currentRoom.addEquipment(equipment);

            gameState.getEventManager().notifyObservers(
                    new GameEvent(GameEventType.ITEM_DROPPED,
                            "Player dropped equipment: " + value,
                            gameState)
            );

            return "You drop: " + value;
        }

        Item item = player.getItem(value);
        if (item != null) {
            player.getInventory().remove(item);
            currentRoom.addItem(item);

            gameState.getEventManager().notifyObservers(
                    new GameEvent(GameEventType.ITEM_DROPPED,
                            "Player dropped item: " + value,
                            gameState)
            );

            return "You drop: " + value;
        }

        return "You cannot drop " + this.value;
    }

    @Override
    public String toString() {
        return "Drop Command: " + this.value;
    }
}