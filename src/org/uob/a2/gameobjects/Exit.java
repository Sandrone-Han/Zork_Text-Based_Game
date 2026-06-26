package org.uob.a2.gameobjects;

public class Exit extends GameObject {
    private String nextRoom;

    public Exit(String id,String name, String description, String nextRoom, boolean hidden) {
        super(id, name, description, hidden);
        this.nextRoom = nextRoom;
    }

    public String getNextRoom() {
        return nextRoom;
    }

    public String getId() {
        return id;
    }
    @Override
    public String toString() {
        return super.toString() + ", nextRoom=" + nextRoom;
    }
}
