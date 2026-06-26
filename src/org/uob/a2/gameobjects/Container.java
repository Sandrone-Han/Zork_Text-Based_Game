package org.uob.a2.gameobjects;

import java.util.ArrayList;

public class Container extends Feature {
    private ArrayList<Item> items;
    private boolean isOpen;
    public Container(String id, String name, String description, boolean hidden) {
        super(id, name, description, hidden);
        this.isOpen = false;
        this.items = new ArrayList<>();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public boolean getHidden(){
        return hidden;
    }
    public void addItem(Item item) {
        this.items.add(item);
    }

    public void open(){
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


    @Override
    public String toString() {
        return "Container {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", hidden=" + getHidden() +
                '}';

    }

}
