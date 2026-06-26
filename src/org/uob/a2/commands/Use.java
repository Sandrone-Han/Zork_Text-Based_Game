package org.uob.a2.commands;

import org.uob.a2.events.GameEvent;
import org.uob.a2.events.GameEventType;
import org.uob.a2.gameobjects.Equipment;
import org.uob.a2.gameobjects.Feature;
import org.uob.a2.gameobjects.GameObject;
import org.uob.a2.gameobjects.GameState;
import org.uob.a2.gameobjects.Player;
import org.uob.a2.gameobjects.Room;
import org.uob.a2.gameobjects.UseInformation;

public class Use extends Command {
    private String equipmentName;
    private String target;

    public Use(String equipmentName, String target) {
        this.commandType = CommandType.USE;
        this.value = equipmentName;
        this.equipmentName = equipmentName;
        this.target = target;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        Room currentRoom = gameState.getMap().getCurrentRoom();

        Equipment equipment = player.getEquipment(this.value);
        if (equipment == null) {
            return "You do not have " + this.value;
        }

        if (equipment.getUseInformation().isUsed()) {
            return "You have already used " + this.value;
        }

        GameObject targetObject = null;

        for (Feature feature : currentRoom.getFeatures()) {
            if (feature.getName().equalsIgnoreCase(this.target)) {
                targetObject = feature;
                break;
            }
        }

        if (targetObject == null) {
            return "Invalid use target";
        }

        UseInformation useInfo = equipment.getUseInformation();

        if (!useInfo.getTarget().equals(targetObject.getId())) {
            return "Invalid use target";
        }

        String result = useInfo.getMessage();
        useInfo.setUsed(true);

        gameState.getEventManager().notifyObservers(
                new GameEvent(GameEventType.EQUIPMENT_USED,
                        "Player used " + this.value + " on " + this.target,
                        gameState)
        );

        return result;
    }

    @Override
    public String toString() {
        return "Use Command: " + this.value + " on " + this.target;
    }
}