package org.example.tests.domen;


import org.example.domen.entities.*;
import org.example.domen.enums.Type;
import org.example.domen.support.LifeSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


class PolicemenTests {
    private Policemen policemen;
    private Equipment weapon;
    private LifeSupport lifeSupport;
    private Planet planet;

    @BeforeEach
    void setUp() {
        weapon = new Weapon("weapon", 10, policemen);
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(weapon);
        planet = Planet.createPlanet("Magrathea", Type.METHAN);
        policemen = new Policemen("Officer John", 100, Type.OXYGEN, new Magrathea("Magrathea", Type.OXYGEN), true, equipmentList, false, 100);
        lifeSupport = policemen.lifeSupport;
    }

    @Test
    void testHoldWeapon() {
        assertFalse(policemen.isHoldingWeapon());
        policemen.holdWeapon();
        assertTrue(policemen.isHoldingWeapon());
        assertFalse(policemen.getEquipmentList().contains(weapon));
    }

    @Test
    void testDropWeapon() {
        policemen.holdWeapon();
        policemen.dropWeapon();
        assertFalse(policemen.isHoldingWeapon());
    }

    @Test
    void testOnLifeSupportDepleted() {
        lifeSupport.consumeResource(50);
        assertEquals(100, policemen.getHealth());
        assertTrue(lifeSupport.getResourceLevel() > 0);
    }

    @Test
    void testHoldWeapon_AlreadyHolding() {

        policemen.setHoldingWeapon(true);
        policemen.holdWeapon();

        assertTrue(policemen.isHoldingWeapon());
    }

    @Test
    void testHoldWeapon_PicksWeapon() {
        List<Equipment> equipment = new ArrayList<>();
        equipment.add(weapon);

        policemen.holdWeapon();

        assertTrue(policemen.getEquipmentList().isEmpty());
    }

    @Test
    void testHoldWeapon_NoWeapon() {
        Policemen person = new Policemen("John", 100, Type.METHAN, planet, true, new ArrayList<>(), false, 10);

        person.holdWeapon();

        assertFalse(person.isHoldingWeapon());
    }


}