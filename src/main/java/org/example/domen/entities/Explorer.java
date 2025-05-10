package org.example.domen.entities;

import org.example.domen.enums.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Explorer extends Person {
    private final Map<String, String> investigationLog = new HashMap<>();

    public Explorer(String name, Integer health, Type type, Planet planet, boolean isAlive, List<Equipment> equipment, int lifeCapacity) {
        super(name, health, type, planet, isAlive, equipment, lifeCapacity);
    }

    private void logInvestigation(Person body, String info) {
        investigationLog.compute(body.getName(), (k, v) -> (v == null ? "" : v + "; ") + info);
    }

    public boolean inspectBody(Person body) {
        String status = body.getIsAlive() ? "alive" : "dead";
        logInvestigation(body, status);
        return body.getIsAlive();
    }

    public void determineType(Person body) {
        String typeInfo = switch (body.getType()) {
            case METHAN -> "methan";
            case OXYGEN -> "oxygen";
            default -> "unknown";
        };
        logInvestigation(body, typeInfo);
    }

    public void checkEquipment(Person body) {
        if (body.getEquipmentList().isEmpty()) {
            logInvestigation(body, "no equipment");
        } else {
            StringBuilder equipmentInfo = new StringBuilder("equipment: ");
            for (Equipment eq : body.getEquipmentList()) {
                equipmentInfo.append(eq.getName()).append(" (frazzle: ").append(eq.getFrazzle()).append("), ");
            }
            logInvestigation(body, equipmentInfo.toString());
        }
    }

    public Equipment retrieveObjectFromBody(Person body, Equipment object) {
        if (!body.getIsAlive()) {
            boolean removed = body.getEquipmentList().removeIf(eq -> eq.equals(object));
            if (removed) {
                logInvestigation(body, "retrieved: " + object.getName());
                return object;
            }
        }
        return null;
    }

    public Map<String, String> getInvestigationLog() {
        return investigationLog;
    }
}
