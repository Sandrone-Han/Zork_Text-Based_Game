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
        return  "Using " + this.getName() + " on " + target.getName()+" You opened the chest!";
    }

    @Override
    public String toString() {
        return super.toString() + ", useInformation=" + useInformation;
    }
}