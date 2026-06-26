package org.uob.a2.gameobjects;

import org.uob.a2.events.EventManager;
import org.uob.a2.events.GameEvent;
import org.uob.a2.events.GameEventType;

public class GameState {
    private Map map;
    private Player player;
    private int score;
    private EventManager eventManager;

    public GameState(Map map, Player player) {
        this.map = map;
        this.player = player;
        this.score = 0;
        this.eventManager = new EventManager();
    }

    public GameState() {
        this.map = null;
        this.player = null;
        this.score = 0;
        this.eventManager = new EventManager();
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
        notifyScoreChanged();
    }

    public void minScore(int removeScore) {
        score -= removeScore;
        notifyScoreChanged();
    }

    public double totalScore() {
        return score;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    private void notifyScoreChanged() {
        if (eventManager != null) {
            eventManager.notifyObservers(
                    new GameEvent(GameEventType.SCORE_CHANGED, "Score is now " + score, this)
            );
        }
    }

    @Override
    public String toString() {
        return "GameState {" +
                "map=" + (map != null ? map.toString() : "null") + ", " +
                "player=" + (player != null ? player.toString() : "null") +
                '}';
    }
}