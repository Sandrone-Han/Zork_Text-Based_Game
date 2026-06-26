package org.uob.a2.factory;

import org.junit.jupiter.api.Test;
import org.uob.a2.gameobjects.Container;
import org.uob.a2.gameobjects.Equipment;
import org.uob.a2.gameobjects.Exit;
import org.uob.a2.gameobjects.Item;
import org.uob.a2.gameobjects.Player;
import org.uob.a2.gameobjects.Room;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectFactoryTest {

    @Test
    void testCreatePlayer() {
        String[] data = {"Tester"};

        Player player = GameObjectFactory.createPlayer(data);

        assertEquals("Tester", player.getName());

        System.out.println("AUTOMARK::GameObjectFactory.testCreatePlayer: PASS");
    }

    @Test
    void testCreateRoom() {
        String[] data = {"r1", "Hall", "A dark hall.", "false"};

        Room room = GameObjectFactory.createRoom(data);

        assertEquals("r1", room.getId());
        assertEquals("Hall", room.getName());
        assertEquals("A dark hall.", room.getDescription());
        assertFalse(room.getHidden());

        System.out.println("AUTOMARK::GameObjectFactory.testCreateRoom: PASS");
    }

    @Test
    void testCreateItem() {
        String[] data = {"i1", "key", "A small key.", "false"};

        Item item = GameObjectFactory.createItem(data);

        assertEquals("i1", item.getId());
        assertEquals("key", item.getName());
        assertEquals("A small key.", item.getDescription());
        assertFalse(item.getHidden());

        System.out.println("AUTOMARK::GameObjectFactory.testCreateItem: PASS");
    }

    @Test
    void testCreateEquipment() {
        String[] data = {
                "e1",
                "key",
                "A key used to open a door.",
                "false",
                "open",
                "door1",
                "unlock",
                "The door is now unlocked."
        };

        Equipment equipment = GameObjectFactory.createEquipment(data);

        assertEquals("e1", equipment.getId());
        assertEquals("key", equipment.getName());
        assertEquals("A key used to open a door.", equipment.getDescription());
        assertFalse(equipment.getHidden());

        assertNotNull(equipment.getUseInformation());
        assertEquals("open", equipment.getUseInformation().getAction());
        assertEquals("door1", equipment.getUseInformation().getTarget());
        assertEquals("unlock", equipment.getUseInformation().getResult());
        assertEquals("The door is now unlocked.", equipment.getUseInformation().getMessage());

        System.out.println("AUTOMARK::GameObjectFactory.testCreateEquipment: PASS");
    }

    @Test
    void testCreateContainer() {
        String[] data = {"c1", "chest", "An old wooden chest.", "true"};

        Container container = GameObjectFactory.createContainer(data);

        assertEquals("c1", container.getId());
        assertEquals("chest", container.getName());
        assertEquals("An old wooden chest.", container.getDescription());
        assertTrue(container.getHidden());

        System.out.println("AUTOMARK::GameObjectFactory.testCreateContainer: PASS");
    }

    @Test
    void testCreateExit() {
        String[] data = {"x1", "north", "A passage to the north.", "r2", "false"};

        Exit exit = GameObjectFactory.createExit(data);

        assertEquals("x1", exit.getId());
        assertEquals("north", exit.getName());
        assertEquals("A passage to the north.", exit.getDescription());
        assertEquals("r2", exit.getNextRoom());
        assertFalse(exit.getHidden());

        System.out.println("AUTOMARK::GameObjectFactory.testCreateExit: PASS");
    }
}