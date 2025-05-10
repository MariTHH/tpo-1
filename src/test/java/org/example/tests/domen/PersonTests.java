package org.example.tests.domen;

import org.example.domen.entities.*;
import org.example.domen.enums.Type;
import org.example.domen.support.LifeSupport;
import org.example.tests.extensions.TestPrivateMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTests {
    private Person person;
    private Planet planet;
    private List<Equipment> equipmentList;
    private Spacesuit spacesuit;
    private LifeSupport lifeSupport;

    @BeforeEach
    void setUp() {
        planet = Planet.createPlanet("Magrathea", Type.METHAN);
        equipmentList = new ArrayList<>();
        spacesuit = new Spacesuit("Methan Suit", 100, Type.METHAN);
        equipmentList.add(spacesuit);
        lifeSupport = new LifeSupport(Type.METHAN, 100);
        person = Person.createPerson("explorer", "James", 100, Type.METHAN, planet, true, equipmentList, 90);
        person.setLifeSupport(lifeSupport);
    }

    @ParameterizedTest
    @CsvSource({
            "explorer, John, 100, METHAN, true, 100",
            "policeman, Alex, 80, OXYGEN, false, 100"
    })
    void testCreatePerson_Valid(String type, String name, int health, Type raceType, boolean isAlive, int lifeSupport) {
        Person person = Person.createPerson(type, name, health, raceType, planet, isAlive, equipmentList, lifeSupport);
        person.setLifeSupport(new LifeSupport(raceType, 100));
        assertNotNull(person);
        assertEquals(name, person.getName());
        assertEquals(health, person.getHealth());
        assertEquals(raceType, person.getType());
        assertEquals(isAlive, person.getIsAlive());
    }

    @Test
    void testCreatePerson_InvalidType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Person.createPerson("unknown", "Bob", 100, Type.OXYGEN, planet, true, equipmentList, 100);
        });
        assertTrue(exception.getMessage().contains("Unknown person type"));
    }

    @ParameterizedTest
    @CsvSource({
            "30, 70",
            "100, 0",
    })
    void testLifeSupportConsumption(int consumptionAmount, int expectedResourceLevel) {
        lifeSupport.consumeResource(consumptionAmount);
        assertEquals(expectedResourceLevel, lifeSupport.getResourceLevel());
    }

    @Test
    void testLifeSupportDepletion() {
        lifeSupport.consumeResource(200);
        assertFalse(lifeSupport.isFunctional());
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "hasValidSpacesuit",
            paramTypes = {}
    )
    @Test
    void testHasValidSpacesuit() throws Exception {
        Method method = Person.class.getDeclaredMethod("hasValidSpacesuit");
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(person);
        assertTrue(result);
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "removeBrokenEquipment",
            paramTypes = {}
    )
    @Test
    void testRemoveBrokenEquipment() throws Exception {
        Weapon brokenWeapon = new Weapon("Broken Gun", 20, null);
        brokenWeapon.setFrazzle(120);
        equipmentList.add(brokenWeapon);

        Method method = Person.class.getDeclaredMethod("removeBrokenEquipment");
        method.setAccessible(true);
        method.invoke(person);

        assertFalse(person.getEquipmentList().contains(brokenWeapon));
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "isSpacesuitFunctional",
            paramTypes = {}
    )
    @Test
    void testIsSpacesuitFunctional() throws Exception {
        Method method = Person.class.getDeclaredMethod("isSpacesuitFunctional");
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(person);
        assertTrue(result);

        spacesuit.setFrazzle(0);
        result = (boolean) method.invoke(person);
        assertFalse(result);
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "consumeLifeSupport",
            paramTypes = {int.class}
    )
    @Test
    void testConsumeLifeSupport() throws Exception {
        Method method = Person.class.getDeclaredMethod("consumeLifeSupport", int.class);
        method.setAccessible(true);
        method.invoke(person, 50);
        assertEquals(50, person.getLifeSupportLevel());

        method.invoke(person, 10);
        assertTrue(person.getIsAlive());
    }

    @Test
    void testCanSurvive() {
        assertTrue(person.canSurvive());

        lifeSupport.consumeResource(200);
        assertFalse(person.canSurvive());
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "rechargeLifeSupport",
            paramTypes = {}
    )
    @Test
    void testOnLifeSupportDepleted() throws Exception {
        lifeSupport.consumeResource(100);
        person.onLifeSupportDepleted();
        assertEquals(1, person.getHealth());
        Method method = Person.class.getDeclaredMethod("rechargeLifeSupport");
        method.setAccessible(true);
        method.invoke(person);
        assertTrue(person.getIsAlive());

        person.onLifeSupportDepleted();
        assertFalse(person.getIsAlive());
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "rechargeLifeSupport",
            paramTypes = {}
    )
    @Test
    void testRechargeLifeSupport() throws Exception {
        Method method = Person.class.getDeclaredMethod("rechargeLifeSupport");
        method.setAccessible(true);
        method.invoke(person);
        assertEquals(100, person.getLifeSupportLevel());
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "validateSurvival",
            paramTypes = {}
    )
    @Test
    void testValidateSurvivalWithoutSpacesuit() throws Exception {
        Person person = new Explorer("John", 100, Type.OXYGEN, planet, true, new ArrayList<>(), 100);
        Method method = Person.class.getDeclaredMethod("validateSurvival");
        method.setAccessible(true);
        int initialLifeSupport = person.getLifeSupportLevel();
        method.invoke(person);

        assertTrue(person.getLifeSupportLevel() < initialLifeSupport);
    }

    @TestPrivateMethod(
            className = "org.example.domen.entities.Person",
            methodName = "consumeLifeSupport",
            paramTypes = {int.class}
    )
    @Test
    void testConsumeLifeSupport_TriggersDepletion() throws Exception {
        Person person = new Explorer("John", 100, Type.METHAN, planet, true, new ArrayList<>(), 10);
        Method method = Person.class.getDeclaredMethod("consumeLifeSupport", int.class);
        method.setAccessible(true);
        method.invoke(person, 15);

        assertEquals(0, person.getLifeSupportLevel());
        assertFalse(person.getIsAlive());
    }


}
