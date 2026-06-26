package org.uob.a2.gameobjects;

import java.util.ArrayList;

public class Player {
    private String name;

    private ArrayList<Item> inventory;
    private ArrayList<Equipment> equipment;
    private Room currentRoom;

    public Player(String name) {
        this.name = name;
        inventory = new ArrayList<>();
        equipment = new ArrayList<>();
    }

    public Player() {
        inventory = new ArrayList<>();
        equipment = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public boolean hasItem(String itemName) {
        for (Item item : this.inventory) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName) {
        for (Item item : this.inventory) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public ArrayList<Equipment> getEquipment() {
        return this.equipment;
    }

    public boolean hasEquipment(String equipmentName) {
        for (Equipment equipment : this.equipment) {
            if (equipment.getName().equals(equipmentName)) {
                return true;
            }
        }
        return false;
    }

    public Equipment getEquipment(String equipmentName) {
        for (Equipment equipment : this.equipment) {
            if (equipment.getName().equals(equipmentName)) {
                return equipment;
            }
        }
        return null;
    }

    public void addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Player Name: " + this.name + "\nInventory:\n");
        for (Item i : this.inventory) {
            out.append("- ").append(i.getDescription()).append("\n");
        }
        out.append("Equipment:\n");
        for (Equipment e : this.equipment) {
            out.append("- ").append(e.getDescription()).append("\n");
        }
        return out.toString();
    }
}