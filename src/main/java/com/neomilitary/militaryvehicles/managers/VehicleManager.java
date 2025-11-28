package com.neomilitary.militaryvehicles.managers;

import com.neomilitary.militaryvehicles.vehicles.MilitaryVehicle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.*;

public class VehicleManager {
    private final Map<UUID, MilitaryVehicle> playerVehicles = new HashMap<>();

    public enum VehicleType {
        T72_TANK("Т-72 Танк", 1000, 120, "TANK"),
        BMP2_IFV("БМП-2", 800, 90, "IFV"),
        MI24_HELICOPTER("Ми-24", 1500, 200, "HELICOPTER"),
        KURGANETS_APC("Курганец-25", 700, 80, "APC"),
        TIGR_ARMORED("Тигр", 500, 150, "ARMORED_CAR"),
        T90_TANK("Т-90 Танк", 1200, 130, "TANK");

        private final String displayName;
        private final int health;
        private final int speed;
        private final String configKey;

        VehicleType(String displayName, int health, int speed, String configKey) {
            this.displayName = displayName;
            this.health = health;
            this.speed = speed;
            this.configKey = configKey;
        }

        public String getDisplayName() { return displayName; }
        public int getHealth() { return health; }
        public int getSpeed() { return speed; }
        public String getConfigKey() { return configKey; }
    }

    public enum UnitMarking {
        Z("Z", "§fZ"),
        V("V", "§fV"),
        O("O", "§fO"),
        BORDER("ⓑ", "§6ⓑ"),
        PATROL("ⓟ", "§eⓟ"),
        GUARD("ⓖ", "§aⓖ"),
        NONE("", "");

        private final String symbol;
        private final String display;

        UnitMarking(String symbol, String display) {
            this.symbol = symbol;
            this.display = display;
        }

        public String getSymbol() { return symbol; }
        public String getDisplay() { return display; }

        public static UnitMarking fromString(String text) {
            for (UnitMarking marking : UnitMarking.values()) {
                if (marking.name().equalsIgnoreCase(text)) {
                    return marking;
                }
            }
            return NONE;
        }
    }

    public boolean spawnVehicle(Player player, VehicleType type, Location location, UnitMarking marking) {
        if (playerVehicles.containsKey(player.getUniqueId())) {
            player.sendMessage("§cУ вас уже есть активная техника!");
            return false;
        }

        try {
            MilitaryVehicle vehicle = new MilitaryVehicle(player, type, location, marking);
            playerVehicles.put(player.getUniqueId(), vehicle);

            player.sendMessage("§a=================================");
            player.sendMessage("§aСоздана техника: " + type.getDisplayName());
            player.sendMessage("§aОбозначение: " + marking.getDisplay());
            player.sendMessage("§aЗдоровье: " + type.getHealth());
            player.sendMessage("§a=================================");
            return true;
        } catch (Exception e) {
            player.sendMessage("§cОшибка при создании техники: " + e.getMessage());
            return false;
        }
    }

    public void removeVehicle(Player player) {
        MilitaryVehicle vehicle = playerVehicles.remove(player.getUniqueId());
        if (vehicle != null) {
            vehicle.remove();
            player.sendMessage("§eТехника удалена");
        } else {
            player.sendMessage("§cУ вас нет активной техники");
        }
    }

    public MilitaryVehicle getPlayerVehicle(Player player) {
        return playerVehicles.get(player.getUniqueId());
    }

    public void removeAllVehicles() {
        for (MilitaryVehicle vehicle : playerVehicles.values()) {
            vehicle.remove();
        }
        playerVehicles.clear();
    }

    public boolean hasVehicle(Player player) {
        return playerVehicles.containsKey(player.getUniqueId());
    }
}