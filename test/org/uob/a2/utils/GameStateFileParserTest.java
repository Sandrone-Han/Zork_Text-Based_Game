package org.uob.a2.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.uob.a2.gameobjects.GameState;

class GameStateFileParserTest {
    @TempDir
    Path tempDir;

    @Test
    void parsesValidJsonGame() throws IOException {
        Path file = write("valid.json", """
                {
                  "player": "Hero",
                  "initialRoomId": "room1",
                  "rooms": [
                    {
                      "id": "room1",
                      "name": "Living Room",
                      "description": "A cozy living room, with a fireplace.",
                      "items": [
                        {"id": "key", "name": "Key", "description": "A rusty key."}
                      ],
                      "containers": [
                        {"id": "chest", "name": "Chest", "description": "A dusty chest."}
                      ],
                      "equipment": [
                        {
                          "id": "crowbar",
                          "name": "Crowbar",
                          "description": "A steel crowbar.",
                          "use": {
                            "action": "open",
                            "target": "chest",
                            "result": "key",
                            "message": "The chest opens."
                          }
                        }
                      ],
                      "exits": [
                        {"id": "north", "name": "North", "description": "A northern door.", "targetRoom": "room2"}
                      ]
                    },
                    {"id": "room2", "name": "Hall", "description": "A dark hall."}
                  ]
                }
                """);

        GameState state = GameStateFileParser.parse(file.toString());

        assertEquals("Hero", state.getPlayer().getName());
        assertEquals("Living Room", state.getMap().getCurrentRoom().getName());
        assertEquals("A cozy living room, with a fireplace.", state.getMap().getCurrentRoom().getDescription());
        assertEquals(1, state.getMap().getCurrentRoom().getItems().size());
        assertEquals(1, state.getMap().getCurrentRoom().getFeatures().size());
        assertEquals(1, state.getMap().getCurrentRoom().getEquipments().size());
        assertEquals(1, state.getMap().getCurrentRoom().getExits().size());
    }

    @Test
    void rejectsMalformedJsonWithLineAndFile() throws IOException {
        Path file = write("malformed.json", "{\n  \"player\": \"Hero\",\n  broken\n}");
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains(file.toAbsolutePath().toString()));
        assertTrue(error.getMessage().contains("line 3"));
    }

    @Test
    void rejectsMissingRequiredField() throws IOException {
        Path file = write("missing.json", """
                {"player":"Hero","initialRoomId":"room1","rooms":[{"id":"room1","name":"Room"}]}
                """);
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains("rooms[0].description"));
        assertTrue(error.getMessage().contains(file.toAbsolutePath().toString()));
    }

    @Test
    void rejectsDuplicateRoomId() throws IOException {
        Path file = write("duplicate.json", """
                {"player":"Hero","initialRoomId":"room1","rooms":[
                  {"id":"room1","name":"One","description":"One."},
                  {"id":"room1","name":"Two","description":"Two."}
                ]}
                """);
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains("duplicates room id 'room1'"));
    }

    @Test
    void rejectsUnknownInitialRoom() throws IOException {
        Path file = write("initial.json", """
                {"player":"Hero","initialRoomId":"missing","rooms":[
                  {"id":"room1","name":"One","description":"One."}
                ]}
                """);
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains("initialRoomId references unknown room 'missing'"));
    }

    @Test
    void rejectsExitToUnknownRoom() throws IOException {
        Path file = write("exit.json", """
                {"player":"Hero","initialRoomId":"room1","rooms":[{
                  "id":"room1","name":"One","description":"One.",
                  "exits":[{"id":"exit1","name":"North","description":"Door.","targetRoom":"missing"}]
                }]}
                """);
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains("targetRoom references unknown room 'missing'"));
    }

    @Test
    void rejectsEquipmentReferenceToUnknownObject() throws IOException {
        Path file = write("use.json", """
                {"player":"Hero","initialRoomId":"room1","rooms":[{
                  "id":"room1","name":"One","description":"One.",
                  "equipment":[{
                    "id":"key","name":"Key","description":"Key.",
                    "use":{"action":"open","target":"missing","result":"room1","message":"Open."}
                  }]
                }]}
                """);
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains("use.target references unknown id 'missing'"));
    }

    @Test
    void rejectsWrongFieldType() throws IOException {
        Path file = write("type.json", """
                {"player":"Hero","initialRoomId":"room1","rooms":[
                  {"id":"room1","name":"One","description":"One.","hidden":"false"}
                ]}
                """);
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains("rooms[0].hidden must be a boolean"));
    }

    @Test
    void rejectsMissingFile() {
        Path file = tempDir.resolve("absent.json");
        GameDataException error = assertThrows(GameDataException.class,
                () -> GameStateFileParser.parse(file.toString()));
        assertTrue(error.getMessage().contains("file does not exist"));
    }

    private Path write(String name, String content) throws IOException {
        Path file = tempDir.resolve(name);
        Files.writeString(file, content);
        return file;
    }
}
