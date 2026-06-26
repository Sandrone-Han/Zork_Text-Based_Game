package org.uob.a2.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.uob.a2.factory.GameObjectFactory;
import org.uob.a2.gameobjects.Container;
import org.uob.a2.gameobjects.Equipment;
import org.uob.a2.gameobjects.Exit;
import org.uob.a2.gameobjects.GameState;
import org.uob.a2.gameobjects.Item;
import org.uob.a2.gameobjects.Map;
import org.uob.a2.gameobjects.Player;
import org.uob.a2.gameobjects.Room;

/**
 * Utility class for parsing a game state from a file.
 *
 * <p>
 * This class reads a structured text file and delegates object creation to
 * GameObjectFactory. The parser is therefore responsible for file reading and
 * object placement, while the factory is responsible for object construction.
 * </p>
 */
public class GameStateFileParser {

    public static GameState parse(String filePath) {
        Map gameMap = new Map();
        Player player = new Player("Default Player");
        String currentRoomId = null;
        Room currentRoom = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(":", 2);
                if (parts.length != 2) {
                    continue;
                }

                String type = parts[0].trim();
                String[] data = parts[1].split(",");

                switch (type) {
                    case "player":
                        player = parsePlayer(data);
                        break;
                    case "map":
                        currentRoomId = parseMap(data);
                        break;
                    case "room":
                        currentRoom = parseRoom(data, gameMap);
                        break;
                    case "item":
                        parseItem(data, currentRoom);
                        break;
                    case "equipment":
                        parseEquipment(data, currentRoom);
                        break;
                    case "container":
                        parseContainer(data, currentRoom);
                        break;
                    case "exit":
                        parseExit(data, currentRoom);
                        break;
                    default:
                        break;
                }
            }

            if (currentRoomId != null) {
                gameMap.setCurrentRoom(currentRoomId);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GameState(gameMap, player);
    }

    private static Player parsePlayer(String[] data) {
        if (data.length < 1) {
            return new Player("Default Player");
        }

        return GameObjectFactory.createPlayer(data);
    }

    private static String parseMap(String[] data) {
        if (data.length < 1) {
            return null;
        }

        return data[0].trim();
    }

    private static Room parseRoom(String[] data, Map gameMap) {
        if (data.length < 4) {
            return null;
        }

        Room room = GameObjectFactory.createRoom(data);
        gameMap.addRoom(room);
        return room;
    }

    private static void parseItem(String[] data, Room currentRoom) {
        if (currentRoom == null || data.length < 4) {
            return;
        }

        Item item = GameObjectFactory.createItem(data);
        currentRoom.addItem(item);
    }

    private static void parseEquipment(String[] data, Room currentRoom) {
        if (currentRoom == null || data.length < 8) {
            return;
        }

        Equipment equipment = GameObjectFactory.createEquipment(data);
        currentRoom.addEquipment(equipment);
    }

    private static void parseContainer(String[] data, Room currentRoom) {
        if (currentRoom == null || data.length < 4) {
            return;
        }

        Container container = GameObjectFactory.createContainer(data);
        currentRoom.addFeature(container);
    }

    private static void parseExit(String[] data, Room currentRoom) {
        if (currentRoom == null || data.length < 5) {
            return;
        }

        Exit exit = GameObjectFactory.createExit(data);
        currentRoom.addExit(exit);
    }
}