package org.example.tests.domen;

import org.example.domen.support.LifeSupport;
import org.example.domen.enums.Type;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LifeSupportTests {

    @ParameterizedTest
    @CsvSource({
            "OXYGEN, 100, 50, true",
            "METHAN, 80, 80, false",
            "OXYGEN, 120, 30, true"
    })
    void testLifeSupportConsumption(Type type, int initialLevel, int consumption, boolean expectedFunctional) {
        LifeSupport lifeSupport = new LifeSupport(type, initialLevel);
        lifeSupport.consumeResource(consumption);
        assertEquals(initialLevel - consumption, lifeSupport.getResourceLevel());
        assertEquals(expectedFunctional, lifeSupport.isFunctional());
    }

    @ParameterizedTest
    @CsvSource({
            "OXYGEN, 100, 100, false, 50, true",
            "METHAN, 80, 80, false, 40, true",
            "OXYGEN, 120, 120, false, 60, true"
    })
    void testLifeSupportRecharge(Type type, int initialLevel, int consumption, boolean expectedBeforeRecharge,
                                 int rechargeAmount, boolean expectedAfterRecharge) {
        LifeSupport lifeSupport = new LifeSupport(type, initialLevel);
        lifeSupport.consumeResource(consumption);
        assertEquals(expectedBeforeRecharge, lifeSupport.isFunctional());

        lifeSupport.recharge(rechargeAmount);
        assertEquals(expectedAfterRecharge, lifeSupport.isFunctional());
    }
}