package org.uob.a2.commands;

import org.uob.a2.events.GameEvent;
import org.uob.a2.events.GameEventType;
import org.uob.a2.gameobjects.Exit;
import org.uob.a2.gameobjects.GameState;
import org.uob.a2.gameobjects.Room;

public class Move extends Command {

    public Move(String direction) {
        this.commandType = CommandType.MOVE;
        this.value = direction;
    }

    @Override
    public String execute(GameState gameState) {
        Room currentRoom = gameState.getMap().getCurrentRoom();
        Exit targetExit = hasExit(currentRoom, this.value);

        if (targetExit != null) {
            gameState.getMap().setCurrentRoom(targetExit.getNextRoom());
            gameState.minScore(2);

            gameState.getEventManager().notifyObservers(
                    new GameEvent(GameEventType.ROOM_CHANGED, "Player moved " + this.value, gameState)
            );

            return "Moving towards " + this.value + "\n";
        }

        return "No exit in that direction.";
    }

    private Exit hasExit(Room currentRoom, String direction) {
        for (Exit exit : currentRoom.getExits()) {
            if (exit.getName().equalsIgnoreCase(direction)) {
                return exit;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Move Command: " + this.value;
    }
}