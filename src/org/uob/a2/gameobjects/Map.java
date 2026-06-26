package org.uob.a2.gameobjects;

import java.util.HashMap;

public class Map {
    private Room currentRoom;
    private HashMap<String, Room> rooms;


    public Map() {
        this.rooms = new HashMap<>();
        this.currentRoom = null;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        this.rooms.put(room.getId(), room);
    }

    public void setCurrentRoom(String roomId) {
        this.currentRoom = this.rooms.get(roomId);
    }

    public void displayMap() {
        StringBuilder mapBuilder = new StringBuilder("Map:\n");
        for (Room room : rooms.values()) {
            mapBuilder.append("RoomId：").append(room.getId()).append("\n");
            mapBuilder.append("Name：").append(room.getName()).append("\n");
            mapBuilder.append("Description：").append(room.getDescription()).append("\n");
            mapBuilder.append("Exit：");
            for (Exit exit : room.getExits()) {
                mapBuilder.append(exit.getName()).append(" to ").append(exit.getNextRoom()).append("；");
            }
            mapBuilder.append("\n\n");
        }
        System.out.println(mapBuilder.toString());
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Map:\n");
        for (Room r : this.rooms.values()) {
            out.append(r.toString()).append("\n");
        }
        return out.toString();
    }
}


