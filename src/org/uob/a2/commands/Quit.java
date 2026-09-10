package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

public class Quit extends Command {

    public Quit(){
        this.commandType = CommandType.QUIT;
        this.value = null;
    }

    @Override
    public String toString() {
        return "Quit command";
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        return "Game over:\n" + player.toString();
    }
}
