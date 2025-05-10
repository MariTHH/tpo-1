package org.example.domen.entities;

public class Weapon extends Equipment {
    private Person owner;

    public Weapon(String name, int frazzle, Person owner) {
        super(name, frazzle);
        this.owner = owner;
    }

}
