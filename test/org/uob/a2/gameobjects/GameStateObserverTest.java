package org.uob.a2.gameobjects;

import org.junit.jupiter.api.Test;
import org.uob.a2.events.GameEvent;
import org.uob.a2.events.GameEventType;
import org.uob.a2.events.GameObserver;

import static org.junit.jupiter.api.Assertions.*;

class GameStateObserverTest {

    private static class ScoreCaptureObserver implements GameObserver {
        private int count = 0;
        private GameEvent lastEvent;

        @Override
        public void onGameEvent(GameEvent event) {
            count++;
            lastEvent = event;
        }
    }

    @Test
    void testAddScoreNotifiesObserver() {
        GameState gameState = new GameState(new Map(), new Player("Tester"));
        ScoreCaptureObserver observer = new ScoreCaptureObserver();

        gameState.getEventManager().addObserver(observer);

        gameState.addScore(10);

        assertEquals(10, gameState.getScore());
        assertEquals(1, observer.count);
        assertEquals(GameEventType.SCORE_CHANGED, observer.lastEvent.getType());
        assertTrue(observer.lastEvent.getMessage().contains("10"));

        System.out.println("AUTOMARK::GameStateObserver.testAddScoreNotifiesObserver: PASS");
    }

    @Test
    void testMinScoreNotifiesObserver() {
        GameState gameState = new GameState(new Map(), new Player("Tester"));
        ScoreCaptureObserver observer = new ScoreCaptureObserver();

        gameState.getEventManager().addObserver(observer);

        gameState.addScore(10);
        gameState.minScore(3);

        assertEquals(7, gameState.getScore());
        assertEquals(2, observer.count);
        assertEquals(GameEventType.SCORE_CHANGED, observer.lastEvent.getType());
        assertTrue(observer.lastEvent.getMessage().contains("7"));

        System.out.println("AUTOMARK::GameStateObserver.testMinScoreNotifiesObserver: PASS");
    }
}