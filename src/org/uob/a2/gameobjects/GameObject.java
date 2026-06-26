package org.uob.a2.gameobjects;

public abstract class GameObject {
    protected String id;
    protected String name;
    protected String description;
    protected boolean hidden;

    public GameObject(String id, String name, String description, boolean hidden) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hidden = hidden;
    }

    public GameObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean getHidden() {
        return hidden;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GameObject {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hidden=" + hidden +
                '}';
    }
}
