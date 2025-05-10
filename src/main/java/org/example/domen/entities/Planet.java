package org.example.domen.entities;

import org.example.domen.enums.Type;

public abstract class Planet {
    protected String name;
    protected Type type;

    public Planet(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public static Planet createPlanet(String name, Type type) {
        return switch (name.toLowerCase()) {
            case "magrathea" -> new Magrathea(name, type);
            case "kappablagulona" -> new KappaBlagulona(name, type);
            default -> throw new IllegalArgumentException("Unknown planet type: " + type);
        };
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
