package org.example.domen.support;

import org.example.domen.enums.Type;
import org.example.domen.interfaces.LifeSupportObserver;

import java.util.ArrayList;
import java.util.List;

public class LifeSupport {
    private int resourceLevel;
    private final Type requiredType;
    private List<LifeSupportObserver> observers = new ArrayList<>();

    public LifeSupport(Type requiredType, int initialResource) {
        this.resourceLevel = initialResource;
        this.requiredType = requiredType;
    }

    public void addObserver(LifeSupportObserver observer) {
        observers.add(observer);
    }

    public boolean isFunctional() {
        return resourceLevel > 0;
    }

    public int getResourceLevel() {
        return resourceLevel;
    }

    public void consumeResource(int amount) {
        if (isFunctional()) {
            resourceLevel -= amount;
            if (resourceLevel <= 0) {
                notifyObservers();
            }
        }
    }

    private void notifyObservers() {
        for (LifeSupportObserver observer : observers) {
            observer.onLifeSupportDepleted();
        }
    }


    public void recharge(int amount) {
        if (amount > 0) {
            resourceLevel = amount;
        }
    }

    public void setResourceLevel(int resourceLevel) {
        this.resourceLevel = resourceLevel;
    }
}
