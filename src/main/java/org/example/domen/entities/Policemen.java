package org.example.domen.entities;

import org.example.domen.interfaces.LifeSupportObserver;
import org.example.domen.support.LifeSupport;
import org.example.domen.enums.Type;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Policemen extends Person implements LifeSupportObserver {
    private boolean holdingWeapon;
    private static final Logger LOGGER = LoggerFactory.getLogger(Policemen.class);

    public Policemen(String name, Integer health, Type type, Planet planet, Boolean isAlive, List<Equipment> equipmentList, boolean holdingWeapon, int lifeSupportCapacity) {
        super(name, health, type, planet, isAlive, equipmentList, lifeSupportCapacity);
        this.holdingWeapon = holdingWeapon;
        this.lifeSupport = new LifeSupport(type, lifeSupportCapacity);
        this.lifeSupport.addObserver(this);
    }

    public boolean isHoldingWeapon() {
        return holdingWeapon;
    }

    @Override
    public void onLifeSupportDepleted() {
        if (canSurvive()) {
            rechargeLifeSupport();
        } else {
            isAlive = false;
        }
    }

    public void holdWeapon() {
        if (holdingWeapon) {
            LOGGER.info(getName() + " is already holding a weapon.");
            return;
        }
        Optional<Equipment> weapon = equipmentList.stream()
                .filter(eq -> eq instanceof Weapon)
                .findFirst();

        if (weapon.isPresent()) {
            holdingWeapon = true;
            equipmentList.remove(weapon.get());
        }
    }

    public void dropWeapon() {
        holdingWeapon = false;
    }

    public void setHoldingWeapon(boolean holdingWeapon) {
        this.holdingWeapon = holdingWeapon;
    }

}
