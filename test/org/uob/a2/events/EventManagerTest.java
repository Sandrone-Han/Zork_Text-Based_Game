package org.uob.a2.events;

import org.junit.jupiter.api.Test;
import org.uob.a2.gameobjects.GameState;

import static org.junit.jupiter.api.Assertions.*;

class EventManagerTest {

    private static class TestObserver implements GameObserver {
        private int count = 0;
        private GameEvent lastEvent;

        @Override
        public void onGameEvent(GameEvent event) {
            count++;
            lastEvent = event;
        }
    }

    @Test
    void testObserverReceivesEvent() {
        EventManager eventManager = new EventManager();
        TestObserver observer = new TestObserver();

        eventManager.addObserver(observer);

        GameEvent event = new GameEvent(
                GameEventType.ROOM_CHANGED,
                "Player moved north.",
                new GameState()
        );

        eventManager.notifyObservers(event);

        assertEquals(1, observer.count);
        assertEquals(GameEventType.ROOM_CHANGED, observer.lastEvent.getType());
        assertEquals("Player moved north.", observer.lastEvent.getMessage());

        System.out.println("AUTOMARK::EventManager.testObserverReceivesEvent: PASS");
    }

    @Test
    void testRemovedObserverDoesNotReceiveEvent() {
        EventManager eventManager = new EventManager();
        TestObserver observer = new TestObserver();

        eventManager.addObserver(observer);
        eventManager.removeObserver(observer);

        GameEvent event = new GameEvent(
                GameEventType.ITEM_PICKED_UP,
                "Player picked up key.",
                new GameState()
        );

        eventManager.notifyObservers(event);

        assertEquals(0, observer.count);

        System.out.println("AUTOMARK::EventManager.testRemovedObserverDoesNotReceiveEvent: PASS");
    }

    @Test
    void testDuplicateObserverIsNotAddedTwice() {
        EventManager eventManager = new EventManager();
        TestObserver observer = new TestObserver();

        eventManager.addObserver(observer);
        eventManager.addObserver(observer);

        GameEvent event = new GameEvent(
                GameEventType.SCORE_CHANGED,
                "Score is now 10.",
                new GameState()
        );

        eventManager.notifyObservers(event);

        assertEquals(1, observer.count);

        System.out.println("AUTOMARK::EventManager.testDuplicateObserverIsNotAddedTwice: PASS");
    }
}