package org.example.tests.domen;

import org.example.domen.entities.Planet;
import org.example.domen.enums.Type;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class PlanetTests {

    @ParameterizedTest
    @CsvSource({
            "magrathea, METHAN, Magrathea",
            "kappablagulona, OXYGEN, KappaBlagulona"
    })
    void testCreatePlanet_Valid(String name, Type type, String expectedClass) throws ClassNotFoundException {
        Planet planet = Planet.createPlanet(name, type);
        assertNotNull(planet);
        assertEquals(name.toLowerCase(), planet.getName().toLowerCase());
        assertEquals(type, planet.getType());
        assertEquals(Class.forName("org.example.domen.entities." + expectedClass), planet.getClass());
    }

    @ParameterizedTest
    @EnumSource(Type.class)
    void testCreatePlanet_Unknown(Type type) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Planet.createPlanet("unknown", type);
        });
        assertTrue(exception.getMessage().contains("Unknown planet type"));
    }
}