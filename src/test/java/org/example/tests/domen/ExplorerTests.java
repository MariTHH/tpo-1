package org.example.tests.domen;

import org.example.domen.entities.*;
import org.example.domen.enums.Type;
import org.example.tests.extensions.PrivateMethodTestsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PrivateMethodTestsExtension.class)
class ExplorerTests {
    private Explorer explorer;
    private Planet planet;
    private List<Equipment> equipmentList;
    private Person alivePerson;
    private Person deadPerson;
    private Equipment weapon;
    private Equipment spacesuit;

    @Test
    @BeforeEach
    void setUp() {
        planet = Magrathea.createPlanet("Magrathea", Type.OXYGEN);
        equipmentList = new ArrayList<>();
        explorer = new Explorer("John Doe", 100, Type.METHAN, planet, true, equipmentList, 100);
        alivePerson = new Explorer("AlivePerson", 100, Type.METHAN, planet, true, new ArrayList<>(), 10);
        deadPerson = new Explorer("DeadPerson", 0, Type.OXYGEN, planet, false, new ArrayList<>(), 10);

        weapon = new Weapon("Laser Gun", 10, null);
        spacesuit = new Spacesuit("Oxygen Suit", 10, Type.OXYGEN);
    }

    @ParameterizedTest
    @CsvSource({
            "Alice, 100, OXYGEN, KappaBlagulona",
            "Bob, 50, METHAN, Magrathea",
            "Eve, 150, OXYGEN, KappaBlagulona"
    })
    void testExplorerEquipment(String name, int health, Type type, String planetName) {
        Planet planet = new Magrathea(planetName, type);
        Spacesuit spacesuit = new Spacesuit("Standard Suit", 100, type);
        explorer = new Explorer("John Doe", 100, Type.METHAN, planet, true, equipmentList, 100);
        explorer.addEquipment(spacesuit);
        assertEquals(1, explorer.getEquipmentList().size());
        assertEquals("Standard Suit", explorer.getEquipmentList().get(0).getName());
    }

    @Test
    void testExplorerInitialization() {
        assertEquals("John Doe", explorer.getName());
        assertEquals(100, explorer.getHealth());
        assertEquals(Type.METHAN, explorer.getType());
        assertEquals(planet, explorer.getPlanet());
        assertTrue(explorer.getIsAlive());
        assertEquals(0, explorer.getEquipmentList().size());
    }

    @Test
    void testInspectBody_Dead() {
        boolean result = explorer.inspectBody(deadPerson);
        assertFalse(result);

        Map<String, String> log = explorer.getInvestigationLog();
        assertEquals("dead", log.get("DeadPerson"));
    }

    @Test
    void testDetermineType() {
        explorer.determineType(alivePerson);
        explorer.determineType(deadPerson);

        Map<String, String> log = explorer.getInvestigationLog();
        assertEquals("methan", log.get("AlivePerson"));
        assertEquals("oxygen", log.get("DeadPerson"));
    }

    @Test
    void testCheckEquipment_NoEquipment() {
        explorer.checkEquipment(deadPerson);
        Map<String, String> log = explorer.getInvestigationLog();
        assertEquals("no equipment", log.get("DeadPerson"));
    }

    @Test
    void testCheckEquipment_WithEquipment() {
        deadPerson.getEquipmentList().add(weapon);
        deadPerson.getEquipmentList().add(spacesuit);

        explorer.checkEquipment(deadPerson);

        Map<String, String> log = explorer.getInvestigationLog();
        assertTrue(log.get("DeadPerson").contains("equipment: Laser Gun"));
        assertTrue(log.get("DeadPerson").contains("Oxygen Suit"));
    }

    @Test
    void testRetrieveObjectFromBody_Success() {
        deadPerson.getEquipmentList().add(weapon);
        Equipment retrieved = explorer.retrieveObjectFromBody(deadPerson, weapon);

        assertNotNull(retrieved);
        assertEquals("Laser Gun", retrieved.getName());
        assertTrue(deadPerson.getEquipmentList().isEmpty());

        Map<String, String> log = explorer.getInvestigationLog();
        assertEquals("retrieved: Laser Gun", log.get("DeadPerson"));
    }

    @Test
    void testRetrieveObjectFromBody_ItemNotFound() {
        Equipment retrieved = explorer.retrieveObjectFromBody(deadPerson, weapon);

        assertNull(retrieved);
    }
}
