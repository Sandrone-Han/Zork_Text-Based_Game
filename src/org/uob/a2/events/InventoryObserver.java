package org.uob.a2.events;

/**
 * Observes inventory-related events.
 */
public class InventoryObserver implements GameObserver {

    @Override
    public void onGameEvent(GameEvent event) {
        if (event.getType() == GameEventType.ITEM_PICKED_UP ||
                event.getType() == GameEventType.ITEM_DROPPED ||
                event.getType() == GameEventType.EQUIPMENT_USED) {
            System.out.println("[Inventory Observer] " + event.getMessage());
        }
    }
}