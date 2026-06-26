package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;


public class Look extends Command {

    public Look(String target) {
        this.commandType = CommandType.LOOK;
        this.value = target;
    }


    @Override
    public String execute(GameState gameState) {
        Room currentRoom = gameState.getMap().getCurrentRoom();
        StringBuilder looks = new StringBuilder();

        if (this.value == null || this.value.isEmpty() || this.value.equalsIgnoreCase("room")) {
            looks.append(currentRoom.getDescription()).append("\n\n");



            // 添加可见的物品
            boolean hasItems = false;
            looks.append("Items in the room:\n");
            for (Item item : currentRoom.getItems()) {
                if (!item.getHidden()) {
                    hasItems = true;
                    looks.append(item.getName())
                            .append(" (").append(item.getId()).append(")")
                            .append(": ").append(item.getDescription())
                            .append("\n");
                }
            }
            if (!hasItems) {
                looks.append("No visible items.\n");
            }

            // 添加可见的装备
            boolean hasEquipment = false;
            looks.append("\nEquipment in the room:\n");
            for (Equipment equipment : currentRoom.getEquipments()) {
                if (!equipment.getHidden()) {
                    hasEquipment = true;
                    looks.append(equipment.getName())
                            .append(" (").append(equipment.getId()).append(")")
                            .append(": ").append(equipment.getDescription())
                            .append("\n");
                }
            }
            if (!hasEquipment) {
                looks.append("No visible equipment.\n");
            }

            // 添加可见的特征
            boolean hasFeatures = false;
            looks.append("\nFeatures in the room:\n");
            for (Feature feature : currentRoom.getFeatures()) {
                if (!feature.getHidden()) {
                    hasFeatures = true;
                    looks.append(feature.getName())
                            .append(": ").append(feature.getDescription())
                            .append("\n");
                }
            }
            if (!hasFeatures) {
                looks.append("No visible features.\n");
            }

            // 添加出口信息
            looks.append("\nExits:\n");
            for (Exit exit : currentRoom.getExits()) {
                if (!exit.getHidden()) {
                    looks
                            .append(exit.getId())
                            .append(": ")
                            .append(exit.getName())
                            .append(",").append(exit.getDescription())
                            .append("\n");
                }
            }

            return looks.toString();
        }

        if ("exits".equals(value.toLowerCase())) {
            looks.append("The available exits are:\n");
            for (Exit exit : currentRoom.getExits()) {
                if (!exit.getHidden()) {
                    looks
                            .append(exit.getId())
                            .append(": ")
                            .append(exit.getName())
                            .append(",").append(exit.getDescription())
                            .append("\n");
                }
            }
            return looks.toString();
        }

        if ("features".equals(value.toLowerCase())) {
            looks.append("You also see:\n");
            for (Feature feature : currentRoom.getFeatures()) {
                if (!feature.getHidden()) {
                    looks.append(feature.getName())
                            .append(": ").append(feature.getDescription())
                            .append("\n");
                }
            }
            return looks.toString();
        }


        Item item = currentRoom.getItemByName(this.value);
        if (item != null && !item.getHidden()) {
            return "Item: " + item.getName() + " (" + item.getId() + ")\n" +
                    "Description: " + item.getDescription() + "\n";
        }


        Equipment equipment = currentRoom.getEquipmentByName(this.value);
        if (equipment != null && !equipment.getHidden()) {
            return "Equipment: " + equipment.getName() + " (" + equipment.getId() + ")\n" +
                    "Description: " + equipment.getDescription() + "\n";
        }


        Feature feature = currentRoom.getFeatureByName(this.value);
        if (feature != null && !feature.getHidden()) {
            return "Feature: " + feature.getName() + "\n" +
                    "Description: " + feature.getDescription() + "\n";
        }

        return "";
    }

    @Override
    public String toString() {
        return "Look Command: " + (this.value != null ? this.value : "room");
    }
}