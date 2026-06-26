package org.uob.a2.factory;

import org.uob.a2.gameobjects.Container;
import org.uob.a2.gameobjects.Equipment;
import org.uob.a2.gameobjects.Exit;
import org.uob.a2.gameobjects.Item;
import org.uob.a2.gameobjects.Player;
import org.uob.a2.gameobjects.Room;
import org.uob.a2.gameobjects.UseInformation;

/**
 * Factory class responsible for creating game domain objects.
 *
 * <p>
 * This class centralises the construction of rooms, items, equipment,
 * containers, exits, players, and use information. The file parser can then
 * focus on reading data rather than knowing how every game object is built.
 * </p>
 */
public class GameObjectFactory {

    public static Player createPlayer(String[] data) {
        return new Player(data[0].trim());
    }

    public static Room createRoom(String[] data) {
        return new Room(
                data[0].trim(),
                data[1].trim(),
                data[2].trim(),
                Boolean.parseBoolean(data[3].trim())
        );
    }

    public static Item createItem(String[] data) {
        return new Item(
                data[0].trim(),
                data[1].trim(),
                data[2].trim(),
                Boolean.parseBoolean(data[3].trim())
        );
    }

    public static Equipment createEquipment(String[] data) {
        UseInformation useInformation = createUseInformation(data);

        return new Equipment(
                data[0].trim(),
                data[1].trim(),
                data[2].trim(),
                Boolean.parseBoolean(data[3].trim()),
                useInformation
        );
    }

    public static UseInformation createUseInformation(String[] data) {
        return new UseInformation(
                false,
                data[4].trim(),
                data[5].trim(),
                data[6].trim(),
                data[7].trim()
        );
    }

    public static Container createContainer(String[] data) {
        return new Container(
                data[0].trim(),
                data[1].trim(),
                data[2].trim(),
                Boolean.parseBoolean(data[3].trim())
        );
    }

    public static Exit createExit(String[] data) {
        return new Exit(
                data[0].trim(),
                data[1].trim(),
                data[2].trim(),
                data[3].trim(),
                Boolean.parseBoolean(data[4].trim())
        );
    }
}