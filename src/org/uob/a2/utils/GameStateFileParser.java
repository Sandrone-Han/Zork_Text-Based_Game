package org.uob.a2.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.uob.a2.gameobjects.Container;
import org.uob.a2.gameobjects.Equipment;
import org.uob.a2.gameobjects.Exit;
import org.uob.a2.gameobjects.GameState;
import org.uob.a2.gameobjects.Item;
import org.uob.a2.gameobjects.Map;
import org.uob.a2.gameobjects.Player;
import org.uob.a2.gameobjects.Room;
import org.uob.a2.gameobjects.UseInformation;

/** Loads and validates a complete game state from UTF-8 JSON. */
public final class GameStateFileParser {
    private static final ObjectMapper JSON = new ObjectMapper();

    private GameStateFileParser() {
    }

    public static GameState parse(String filePath) {
        Path path = Path.of(filePath).toAbsolutePath().normalize();
        JsonNode root = readJson(path);
        try {
            return parseValidated(root, path);
        } catch (GameDataException e) {
            if (e.getMessage().startsWith("Invalid game data '")) {
                throw e;
            }
            throw invalid(path, e.getMessage(), e);
        }
    }

    private static GameState parseValidated(JsonNode root, Path path) {
        requireObject(root, "root");

        String playerName = requiredText(root, "player", "root");
        String initialRoomId = requiredText(root, "initialRoomId", "root");
        JsonNode roomsNode = requiredArray(root, "rooms", "root");
        if (roomsNode.isEmpty()) {
            throw invalid(path, "rooms must contain at least one room");
        }

        Map map = new Map();
        Set<String> roomIds = new HashSet<>();
        Set<String> objectIds = new HashSet<>();

        for (int roomIndex = 0; roomIndex < roomsNode.size(); roomIndex++) {
            JsonNode roomNode = roomsNode.get(roomIndex);
            String context = "rooms[" + roomIndex + "]";
            requireObject(roomNode, context);
            String roomId = uniqueId(roomNode, context, roomIds, "room");
            Room room = new Room(
                    roomId,
                    requiredText(roomNode, "name", context),
                    requiredText(roomNode, "description", context),
                    optionalBoolean(roomNode, "hidden", context, false));
            map.addRoom(room);
            parseItems(roomNode, room, context, objectIds);
            parseContainers(roomNode, room, context, objectIds);
            parseEquipment(roomNode, room, context, objectIds);
            parseExits(roomNode, room, context, objectIds);
        }

        if (!roomIds.contains(initialRoomId)) {
            throw invalid(path, "initialRoomId references unknown room '" + initialRoomId + "'");
        }
        validateReferences(roomsNode, roomIds, objectIds, path);
        map.setCurrentRoom(initialRoomId);
        return new GameState(map, new Player(playerName));
    }

    private static JsonNode readJson(Path path) {
        if (!Files.isRegularFile(path)) {
            throw invalid(path, "file does not exist or is not a regular file");
        }
        try {
            return JSON.readTree(Files.readString(path));
        } catch (JsonProcessingException e) {
            String location = e.getLocation() == null ? "" :
                    " at line " + e.getLocation().getLineNr() + ", column " + e.getLocation().getColumnNr();
            throw invalid(path, "malformed JSON" + location + ": " + e.getOriginalMessage(), e);
        } catch (IOException e) {
            throw invalid(path, "could not read file: " + e.getMessage(), e);
        }
    }

    private static void parseItems(JsonNode roomNode, Room room, String context, Set<String> ids) {
        JsonNode items = optionalArray(roomNode, "items", context);
        for (int i = 0; i < items.size(); i++) {
            JsonNode node = items.get(i);
            String itemContext = context + ".items[" + i + "]";
            requireObject(node, itemContext);
            room.addItem(new Item(
                    uniqueId(node, itemContext, ids, "object"),
                    requiredText(node, "name", itemContext),
                    requiredText(node, "description", itemContext),
                    optionalBoolean(node, "hidden", itemContext, false)));
        }
    }

    private static void parseContainers(JsonNode roomNode, Room room, String context, Set<String> ids) {
        JsonNode containers = optionalArray(roomNode, "containers", context);
        for (int i = 0; i < containers.size(); i++) {
            JsonNode node = containers.get(i);
            String containerContext = context + ".containers[" + i + "]";
            requireObject(node, containerContext);
            room.addFeature(new Container(
                    uniqueId(node, containerContext, ids, "object"),
                    requiredText(node, "name", containerContext),
                    requiredText(node, "description", containerContext),
                    optionalBoolean(node, "hidden", containerContext, false)));
        }
    }

    private static void parseEquipment(JsonNode roomNode, Room room, String context, Set<String> ids) {
        JsonNode equipment = optionalArray(roomNode, "equipment", context);
        for (int i = 0; i < equipment.size(); i++) {
            JsonNode node = equipment.get(i);
            String equipmentContext = context + ".equipment[" + i + "]";
            requireObject(node, equipmentContext);
            JsonNode use = requiredObject(node, "use", equipmentContext);
            UseInformation useInformation = new UseInformation(
                    false,
                    requiredText(use, "action", equipmentContext + ".use"),
                    requiredText(use, "target", equipmentContext + ".use"),
                    requiredText(use, "result", equipmentContext + ".use"),
                    requiredText(use, "message", equipmentContext + ".use"));
            room.addEquipment(new Equipment(
                    uniqueId(node, equipmentContext, ids, "object"),
                    requiredText(node, "name", equipmentContext),
                    requiredText(node, "description", equipmentContext),
                    optionalBoolean(node, "hidden", equipmentContext, false),
                    useInformation));
        }
    }

    private static void parseExits(JsonNode roomNode, Room room, String context, Set<String> ids) {
        JsonNode exits = optionalArray(roomNode, "exits", context);
        for (int i = 0; i < exits.size(); i++) {
            JsonNode node = exits.get(i);
            String exitContext = context + ".exits[" + i + "]";
            requireObject(node, exitContext);
            room.addExit(new Exit(
                    uniqueId(node, exitContext, ids, "object"),
                    requiredText(node, "name", exitContext),
                    requiredText(node, "description", exitContext),
                    requiredText(node, "targetRoom", exitContext),
                    optionalBoolean(node, "hidden", exitContext, false)));
        }
    }

    private static void validateReferences(JsonNode rooms, Set<String> roomIds, Set<String> objectIds, Path path) {
        for (int roomIndex = 0; roomIndex < rooms.size(); roomIndex++) {
            JsonNode room = rooms.get(roomIndex);
            JsonNode exits = optionalArray(room, "exits", "rooms[" + roomIndex + "]");
            for (int i = 0; i < exits.size(); i++) {
                String target = exits.get(i).get("targetRoom").asText();
                if (!roomIds.contains(target)) {
                    throw invalid(path, "rooms[" + roomIndex + "].exits[" + i +
                            "].targetRoom references unknown room '" + target + "'");
                }
            }

            JsonNode equipment = optionalArray(room, "equipment", "rooms[" + roomIndex + "]");
            for (int i = 0; i < equipment.size(); i++) {
                JsonNode use = equipment.get(i).get("use");
                String target = use.get("target").asText();
                String result = use.get("result").asText();
                if (!objectIds.contains(target) && !roomIds.contains(target)) {
                    throw invalid(path, "rooms[" + roomIndex + "].equipment[" + i +
                            "].use.target references unknown id '" + target + "'");
                }
                if (!objectIds.contains(result) && !roomIds.contains(result)) {
                    throw invalid(path, "rooms[" + roomIndex + "].equipment[" + i +
                            "].use.result references unknown id '" + result + "'");
                }
            }
        }
    }

    private static String uniqueId(JsonNode node, String context, Set<String> ids, String kind) {
        String id = requiredText(node, "id", context);
        if (!ids.add(id)) {
            throw new GameDataException(context + ".id duplicates " + kind + " id '" + id + "'");
        }
        return id;
    }

    private static String requiredText(JsonNode node, String field, String context) {
        JsonNode value = node.get(field);
        if (value == null || !value.isTextual() || value.asText().isBlank()) {
            throw new GameDataException(context + "." + field + " must be a non-empty string");
        }
        return value.asText();
    }

    private static boolean optionalBoolean(JsonNode node, String field, String context, boolean defaultValue) {
        JsonNode value = node.get(field);
        if (value == null) {
            return defaultValue;
        }
        if (!value.isBoolean()) {
            throw new GameDataException(context + "." + field + " must be a boolean");
        }
        return value.asBoolean();
    }

    private static JsonNode requiredArray(JsonNode node, String field, String context) {
        JsonNode value = node.get(field);
        if (value == null || !value.isArray()) {
            throw new GameDataException(context + "." + field + " must be an array");
        }
        return value;
    }

    private static JsonNode optionalArray(JsonNode node, String field, String context) {
        JsonNode value = node.get(field);
        if (value == null) {
            return JSON.createArrayNode();
        }
        if (!value.isArray()) {
            throw new GameDataException(context + "." + field + " must be an array");
        }
        return value;
    }

    private static JsonNode requiredObject(JsonNode node, String field, String context) {
        JsonNode value = node.get(field);
        requireObject(value, context + "." + field);
        return value;
    }

    private static void requireObject(JsonNode node, String context) {
        if (node == null || !node.isObject()) {
            throw new GameDataException(context + " must be an object");
        }
    }

    private static GameDataException invalid(Path path, String detail) {
        return new GameDataException("Invalid game data '" + path + "': " + detail);
    }

    private static GameDataException invalid(Path path, String detail, Throwable cause) {
        return new GameDataException("Invalid game data '" + path + "': " + detail, cause);
    }
}
