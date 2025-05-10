package org.example.domen.entities;

import org.example.domen.enums.Type;
import org.example.domen.interfaces.LifeSupportObserver;
import org.example.domen.support.LifeSupport;

import java.util.List;
import java.util.Random;

public abstract class Person implements LifeSupportObserver {
    protected String name;
    protected Integer health;
    protected Type type;
    protected Planet planet;
    protected boolean isAlive;
    protected List<Equipment> equipmentList;
    public LifeSupport lifeSupport;
    private static final int MAX_FRAZZLE = 100;
    private static final int LIFE_SUPPORT_CONSUMPTION_MAX = 20;
    private static final int LIFE_SUPPORT_FULL_CHARGE = 100;
    private static final int MIN_HEALTH_TO_SURVIVE = 1;


    public Person(String name, Integer health, Type type, Planet planet, boolean isAlive, List<Equipment> equipmentList, int lifeSupportCapacity) {
        this.name = name;
        this.health = health;
        this.type = type;
        this.planet = planet;
        this.isAlive = isAlive;
        this.equipmentList = equipmentList;
        this.lifeSupport = new LifeSupport(type, lifeSupportCapacity);
        this.lifeSupport.addObserver(this);

        validateSurvival();
        removeBrokenEquipment();
    }

    public static Person createPerson(String type, String name, Integer health, Type raceType, Planet planet, boolean isAlive, List<Equipment> equipment, int lifeSupportCapacity) {
        return switch (type.toLowerCase()) {
            case "explorer" -> new Explorer(name, health, raceType, planet, isAlive, equipment, 100);
            case "policeman" -> new Policemen(name, health, raceType, planet, isAlive, equipment, false, 100);
            default -> throw new IllegalArgumentException("Unknown person type: " + type);
        };
    }

    private void validateSurvival() {
        if (this.planet.getType() != this.type || this.lifeSupport.getResourceLevel() < 0) {
            if (!hasValidSpacesuit()) {
                Random random = new Random();
                int consumption = random.nextInt(LIFE_SUPPORT_CONSUMPTION_MAX);
                consumeLifeSupport(consumption);
            }
        }
    }

    private boolean hasValidSpacesuit() {
        for (Equipment equipment : equipmentList) {
            if (equipment instanceof Spacesuit) {
                Spacesuit suit = (Spacesuit) equipment;
                if (suit.getType() == this.planet.getType()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeBrokenEquipment() {
        if (this.equipmentList != null) {
            equipmentList.removeIf(equipment -> equipment.getFrazzle() > MAX_FRAZZLE);
        }
    }

    private boolean isSpacesuitFunctional() {
        return equipmentList.stream()
                .filter(eq -> eq instanceof Spacesuit)
                .anyMatch(eq -> eq.getFrazzle() > 0);
    }

    public boolean canSurvive() {
        return isSpacesuitFunctional() && lifeSupport.isFunctional();
    }

    private void consumeLifeSupport(int amount) {
        lifeSupport.consumeResource(amount);
        if (lifeSupport.getResourceLevel() <= 0) {
            lifeSupport.setResourceLevel(0);
            onLifeSupportDepleted();
        }
    }

    @Override
    public void onLifeSupportDepleted() {
        this.health /= 2;

        if (this.health <= 1) {
            isAlive = false;
            return;
        }
        ;
        onLifeSupportDepleted();
    }

    public int getLifeSupportLevel() {
        return lifeSupport.getResourceLevel();
    }

    void rechargeLifeSupport() {
        lifeSupport.recharge(LIFE_SUPPORT_FULL_CHARGE);
        if (lifeSupport.getResourceLevel() >= 0 && this.health >= MIN_HEALTH_TO_SURVIVE) {
            isAlive = true;
        } else {
            isAlive = false;
        }
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Integer getHealth() {
        return health;
    }

    public Planet getPlanet() {
        return planet;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void addEquipment(Equipment equipment) {
        equipmentList.add(equipment);
    }

    public void setLifeSupport(LifeSupport lifeSupport) {
        this.lifeSupport = lifeSupport;
    }
}
