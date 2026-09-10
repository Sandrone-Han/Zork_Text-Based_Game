package org.uob.a2.gameobjects;

import java.util.ArrayList;

public class Room extends GameObject {


    private ArrayList<Item> items;
    private ArrayList<Equipment> equipment;
    private ArrayList<Feature> features;
    private ArrayList<Exit> exits;
    private ArrayList<Container> containers;


    public Room() {
        super();
        this.items = new ArrayList<>();
        this.equipment = new ArrayList<>();
        this.features = new ArrayList<>();
        this.exits = new ArrayList<>();
        this.containers = new ArrayList<>();
    }

    public Room(String id, String name, String description, boolean hidden) {
        super(id, name, description, hidden);
        this.items = new ArrayList<>();
        this.equipment = new ArrayList<>();
        this.features = new ArrayList<>();
        this.exits = new ArrayList<>();
        this.containers = new ArrayList<>();
    }


    public void setName(String na) {
        super.setName(na);
    }

    public void setDescription(String description) {
        super.setDescription(description);
    }

    public String getName() {
        return super.getName();
    }

    public String getDescription() {
        return super.getDescription();
    }

    public ArrayList<Exit> getExits() {
        return this.exits;
    }

    public void addExit(Exit exit) {
        this.exits.add(exit);
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public Item getItem(String id) {
        for (Item item : this.items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public Item getItemByName(String name) {
        for (Item item : this.items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }


    public void addItem(Item item) {
        this.items.add(item);
    }

    public ArrayList<Feature> getFeatures() {
        return this.features;
    }

    public Feature getFeature(String id) {
        for (Feature feature : this.features) {
            if (feature.getId().equals(id)) {
                return feature;
            }
        }
        return null;
    }

    public Feature getFeatureByName(String name) {
        for (Feature feature : this.features) {
            if (feature.getName().equals(name)) {
                return feature;
            }
        }
        return null;
    }

    public void addFeature(Feature fe) {
        this.features.add(fe);
    }

    public ArrayList<Equipment> getEquipments() {
        return this.equipment;
    }

    public Equipment getEquipment(String id) {
        for (Equipment equip : this.equipment) {
            if (equip.getId().equals(id)) {
                return equip;
            }
        }
        return null;
    }

    public Equipment getEquipmentByName(String name) {
        for (Equipment equip : this.equipment) {
            if (equip.getName().equals(name)) {
                return equip;
            }
        }
        return null;
    }

    public void addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
    }

    public Exit getExit(String id) {
        for (Exit exit : this.exits) {
            if (exit.getId().equals(id)) {
                return exit;
            }
        }
        return null;
    }



    public boolean hasItem(String itemName) {
        for (Item item : this.items) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEquipment(String name) {
        for (Equipment equip : this.equipment) {
            if (equip.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addContainer(Container container) {
        if (container == null) {
            throw new IllegalArgumentException("Cannot add a null container to the room.");
        }
        containers.add(container);
    }

    @Override
    public String toString() {
        String out = "[" + id + "] Room: " + name + "\nDescription: " + description + "\nIn the room there is: ";
        for (Item i : this.items) {
            out += i + "\n";
        }
        for (Equipment e : this.equipment) {
            out += e + "\n";
        }
        for (Feature f : this.features) {
            out += f + "\n";
        }
        for (Exit e : this.exits) {
            out += e + "\n";
        }
        return out + "\n";
    }
}
