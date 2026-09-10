package org.uob.a2.gameobjects;

import java.util.HashMap;

public class Map {
    private Room currentRoom;
    private final HashMap<String, Room> rooms;

    public Map() {
        this.rooms = new HashMap<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    public void setCurrentRoom(String roomId) {
        currentRoom = rooms.get(roomId);
    }

    public void displayMap() {
        StringBuilder output = new StringBuilder("Map:\n");
        for (Room room : rooms.values()) {
            output.append("Room ID: ").append(room.getId()).append('\n');
            output.append("Name: ").append(room.getName()).append('\n');
            output.append("Description: ").append(room.getDescription()).append('\n');
            output.append("Exits: ");
            for (Exit exit : room.getExits()) {
                if (!exit.getHidden()) {
                    output.append(exit.getName()).append(" to ").append(exit.getNextRoom()).append("; ");
                }
            }
            output.append("\n\n");
        }
        System.out.println(output);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Map:\n");
        for (Room room : rooms.values()) {
            output.append(room).append('\n');
        }
        return output.toString();
    }
}
