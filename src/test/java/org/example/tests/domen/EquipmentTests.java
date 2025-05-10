package org.example.tests.domen;

import org.example.domen.entities.*;
import org.example.domen.enums.Type;
import org.example.tests.extensions.PrivateMethodTestsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PrivateMethodTestsExtension.class)
class EquipmentTests {

    @Test
    void testValidSpacesuitCreation() {
        Equipment equipment = Equipment.createEquipment("spacesuit", "Explorer Suit", 50, Type.OXYGEN, null);
        assertNotNull(equipment);
        assertEquals("Explorer Suit", equipment.getName());
        assertEquals(50, equipment.getFrazzle());
    }

    @ParameterizedTest
    @CsvSource({
            "Laser Gun, 30",
            "Plasma Rifle, 50",
            "Energy Sword, 75"
    })
    void testValidWeaponCreation(String weaponName, int frazzle) {
        Person owner = new Explorer("John Doe", 100, Type.METHAN, Planet.createPlanet("Magrathea", Type.METHAN), true, null, 100);
        Equipment equipment = Weapon.createEquipment("weapon", weaponName, frazzle, null, owner);

        assertNotNull(equipment);
        assertEquals(weaponName, equipment.getName());
        assertEquals(frazzle, equipment.getFrazzle());
    }

    @Test
    void testInvalidEquipmentType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Equipment.createEquipment("unknown", "Unknown Item", 20, null, null)
        );
        assertEquals("Unknown equipment type: null", exception.getMessage());
    }

    @Test
    void testTooDamagedEquipmentCreation() {
        Exception exception = assertThrows(IllegalStateException.class, () ->
                Equipment.createEquipment("spacesuit", "Old Suit", 120, Type.OXYGEN, null)
        );
        assertEquals("Equipment is too damaged to be used.", exception.getMessage());
    }

    @Test
    void testGetName() {
        Equipment equipment = new Spacesuit("Test Suit", 25, Type.OXYGEN);
        assertEquals("Test Suit", equipment.getName());
    }

    @Test
    void testGetFrazzle() {
        Equipment equipment = new Spacesuit("Test Suit", 25, Type.OXYGEN);
        assertEquals(25, equipment.getFrazzle());
    }

}
