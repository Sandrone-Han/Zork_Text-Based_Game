package org.uob.a2.commands;

import org.uob.a2.gameobjects.GameState;
import org.uob.a2.gameobjects.Item;
import org.uob.a2.gameobjects.Player;

/** Combines two carried items. Recipe-driven combinations are planned next. */
public class Combine extends Command {
    private final String item1;
    private final String item2;

    public Combine(String item1, String item2) {
        this.commandType = CommandType.COMBINE;
        this.item1 = item1;
        this.item2 = item2;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        Item first = player.getItem(item1);
        Item second = player.getItem(item2);

        if (first == null) {
            return "You do not have " + item1 + ".";
        }
        if (second == null) {
            return "You do not have " + item2 + ".";
        }

        Item combinedItem = combineItems(first, second);
        player.removeItem(first);
        player.removeItem(second);
        player.addItem(combinedItem);
        return "You combine " + item1 + " and " + item2 + " to create " + combinedItem.getName() + ".";
    }

    private Item combineItems(Item first, Item second) {
        String name = first.getName() + " and " + second.getName();
        String description = first.getName() + " combined with " + second.getName() + ".";
        return new Item(first.getId() + second.getId(), name, description, false);
    }

    @Override
    public String toString() {
        return "Combine Command: " + item1 + " and " + item2;
    }
}
