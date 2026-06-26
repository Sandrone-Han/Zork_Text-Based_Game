package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

public class Combine extends Command {
    private String item1;
    private String item2;

    public Combine(String item1, String item2) {
        this.commandType = CommandType.COMBINE;
        this.item1 = item1;
        this.item2 = item2;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        Item itemCombine1 = player.getItem(item1);
        Item itemCombine2 = player.getItem(item2);
        if (itemCombine1!= null && itemCombine2!= null) {

            Item combinedItem = combineItems(itemCombine1, itemCombine2);
            player.addItem(combinedItem);
            player.removeItem(itemCombine1);
            player.removeItem(itemCombine2);
            return "You succeed put" + item1 + " and " + item2 + " together " + combinedItem.getName() + "。";
        } else {
            if (itemCombine1 == null) {
                return "You do not have" + item1 + " items.";
            } else {
                return "You do not have" + item2 + " items.";
            }
        }
    }

    @Override
    public String toString() {
        return "Combine Command: " + item1 + " and " + item2;
    }


    private Item combineItems(Item item1, Item item2) {
        String combinedName = item1.getName() + " and " + item2.getName();
        String combinedDescription = "By" + item1.getName() + " and " + item2.getName() + " combined";
        String id = item1.getId() + item2.getId();
        return new Item(id,combinedName, combinedDescription,false);
    }
}
