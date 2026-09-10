package org.uob.a2.gameobjects;

public class Equipment extends GameObject implements Usable {
    private UseInformation useInformation;
    public Equipment(String id, String name, String description, boolean hidden, UseInformation useInfo) {
        super(id, name, description, hidden);
        this.useInformation = useInfo;
    }


    public void setUseInformation(UseInformation useInformation) {
        this.useInformation = useInformation;
    }


    public UseInformation getUseInformation() {
        return useInformation;
    }

    public String use(Container target, GameState gameState) {
        if (useInformation == null) {
            return "This equipment cannot be used.";
        }
        if (useInformation.isUsed()) {
            return "You have already used " + getName() + ".";
        }
        if (target == null || !useInformation.getTarget().equals(target.getId())) {
            return "Invalid use target";
        }

        target.open();
        Room room = gameState.getMap().getCurrentRoom();
        GameObject revealed = findObject(room, useInformation.getResult());
        if (revealed != null) {
            revealed.setHidden(false);
        }
        useInformation.setUsed(true);
        return useInformation.getMessage();
    }

    private GameObject findObject(Room room, String id) {
        GameObject object = room.getItem(id);
        if (object == null) object = room.getEquipment(id);
        if (object == null) object = room.getFeature(id);
        if (object == null) object = room.getExit(id);
        return object;
    }

    @Override
    public String toString() {
        return super.toString() + ", useInformation=" + useInformation;
    }
}
