package org.uob.a2.events;

import java.util.ArrayList;

/**
 * Manages observers and notifies them when game events occur.
 */
public class EventManager {
    private final ArrayList<GameObserver> observers;

    public EventManager() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(GameObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onGameEvent(event);
        }
    }
}