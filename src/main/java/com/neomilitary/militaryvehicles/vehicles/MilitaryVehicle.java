package com.neomilitary.militaryvehicles.vehicles;

import com.neomilitary.militaryvehicles.managers.VehicleManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import java.util.*;

public class MilitaryVehicle {
    private final Player owner;
    private final VehicleManager.VehicleType type;
    private final VehicleManager.UnitMarking marking;
    private ArmorStand base;
    private ArmorStand turret;
    private ArmorStand markingDisplay;
    private List<ArmorStand> parts = new ArrayList<>();
    private Location location;
    private int health;

    public MilitaryVehicle(Player owner, VehicleManager.VehicleType type,
                           Location location, VehicleManager.UnitMarking marking) {
        this.owner = owner;
        this.type = type;
        this.location = location;
        this.marking = marking;
        this.health = type.getHealth();

        spawnVehicle();
    }

    private void spawnVehicle() {
        // Базовая платформа
        base = spawnArmorStand(location.clone());
        base.setCustomName(type.getDisplayName() + " " + marking.getDisplay());
        base.setCustomNameVisible(true);
        base.setGravity(false);
        base.setVisible(false);

        // Обозначение
        createMarking();

        // Модель техники
        switch (type) {
            case T72_TANK:
            case T90_TANK:
                createTankModel();
                break;
            case BMP2_IFV:
                createIFVModel();
                break;
            case MI24_HELICOPTER:
                createHelicopterModel();
                break;
            case KURGANETS_APC:
                createAPCModel();
                break;
            case TIGR_ARMORED:
                createArmoredCarModel();
                break;
        }

        // Эффекты спавна
        location.getWorld().playSound(location, Sound.BLOCK_ANVIL_USE, 1.0f, 0.8f);
        location.getWorld().spawnParticle(Particle.SMOKE_LARGE, location, 20);
    }

    private void createMarking() {
        Location markingLoc = location.clone().add(0, 2.5, 0);
        markingDisplay = spawnArmorStand(markingLoc);
        markingDisplay.setCustomName(marking.getDisplay());
        markingDisplay.setCustomNameVisible(true);
        markingDisplay.setGravity(false);
        markingDisplay.setVisible(false);
        markingDisplay.setSmall(true);
    }

    private void createTankModel() {
        // Корпус
        ArmorStand hull = spawnArmorStand(location.clone().add(0, 0.5, 0));
        hull.setHelmet(new ItemStack(Material.IRON_BLOCK));
        hull.setGravity(false);
        hull.setVisible(false);
        parts.add(hull);

        // Башня
        Location turretLoc = location.clone().add(0, 1.2, 0);
        turret = spawnArmorStand(turretLoc);
        turret.setHelmet(new ItemStack(Material.IRON_BARS));
        turret.setGravity(false);
        turret.setVisible(false);
        parts.add(turret);

        // Пушка
        ArmorStand cannon = spawnArmorStand(location.clone().add(0, 1.0, 0.8));
        cannon.setHelmet(new ItemStack(Material.BLACK_CONCRETE));
        cannon.setGravity(false);
        cannon.setVisible(false);
        cannon.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));
        parts.add(cannon);

        // Гусеницы
        createTracks();
    }

    private void createIFVModel() {
        // Корпус БМП
        ArmorStand hull = spawnArmorStand(location.clone().add(0, 0.4, 0));
        hull.setHelmet(new ItemStack(Material.GREEN_CONCRETE));
        hull.setGravity(false);
        hull.setVisible(false);
        parts.add(hull);

        // Башня
        Location turretLoc = location.clone().add(0, 1.0, 0);
        turret = spawnArmorStand(turretLoc);
        turret.setHelmet(new ItemStack(Material.GREEN_TERRACOTTA));
        turret.setGravity(false);
        turret.setVisible(false);
        parts.add(turret);

        // Пушка
        ArmorStand cannon = spawnArmorStand(location.clone().add(0, 0.8, 0.6));
        cannon.setHelmet(new ItemStack(Material.GRAY_CONCRETE));
        cannon.setGravity(false);
        cannon.setVisible(false);
        cannon.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));
        parts.add(cannon);
    }

    private void createHelicopterModel() {
        // Корпус вертолета
        ArmorStand hull = spawnArmorStand(location.clone().add(0, 1.0, 0));
        hull.setHelmet(new ItemStack(Material.GRAY_CONCRETE));
        hull.setGravity(false);
        hull.setVisible(false);
        parts.add(hull);

        // Винт
        ArmorStand rotor = spawnArmorStand(location.clone().add(0, 1.8, 0));
        rotor.setHelmet(new ItemStack(Material.IRON_BARS));
        rotor.setGravity(false);
        rotor.setVisible(false);
        parts.add(rotor);
    }

    private void createAPCModel() {
        // Корпус БТР
        ArmorStand hull = spawnArmorStand(location.clone().add(0, 0.6, 0));
        hull.setHelmet(new ItemStack(Material.GREEN_TERRACOTTA));
        hull.setGravity(false);
        hull.setVisible(false);
        parts.add(hull);

        // Башня
        ArmorStand smallTurret = spawnArmorStand(location.clone().add(0, 1.1, 0));
        smallTurret.setHelmet(new ItemStack(Material.GRAY_CONCRETE));
        smallTurret.setGravity(false);
        smallTurret.setVisible(false);
        parts.add(smallTurret);
    }

    private void createArmoredCarModel() {
        // Корпус бронеавтомобиля
        ArmorStand hull = spawnArmorStand(location.clone().add(0, 0.3, 0));
        hull.setHelmet(new ItemStack(Material.BLACK_CONCRETE));
        hull.setGravity(false);
        hull.setVisible(false);
        parts.add(hull);
    }

    private void createTracks() {
        // Левая гусеница
        ArmorStand leftTrack = spawnArmorStand(location.clone().add(-0.8, 0.3, 0));
        leftTrack.setHelmet(new ItemStack(Material.BLACK_CONCRETE));
        leftTrack.setGravity(false);
        leftTrack.setVisible(false);
        parts.add(leftTrack);

        // Правая гусеница
        ArmorStand rightTrack = spawnArmorStand(location.clone().add(0.8, 0.3, 0));
        rightTrack.setHelmet(new ItemStack(Material.BLACK_CONCRETE));
        rightTrack.setGravity(false);
        rightTrack.setVisible(false);
        parts.add(rightTrack);
    }

    private ArmorStand spawnArmorStand(Location loc) {
        ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
        armorStand.setBasePlate(false);
        armorStand.setArms(false);
        armorStand.setMarker(true);
        return armorStand;
    }

    public void moveTo(Location newLocation) {
        Location offset = newLocation.clone().subtract(this.location);

        // Перемещаем все части техники
        if (base != null) base.teleport(base.getLocation().add(offset));
        if (turret != null) turret.teleport(turret.getLocation().add(offset));
        if (markingDisplay != null) markingDisplay.teleport(markingDisplay.getLocation().add(offset));

        for (ArmorStand part : parts) {
            if (part != null) part.teleport(part.getLocation().add(offset));
        }

        this.location = newLocation.clone();
    }

    public void remove() {
        if (base != null) base.remove();
        if (turret != null) turret.remove();
        if (markingDisplay != null) markingDisplay.remove();

        for (ArmorStand part : parts) {
            if (part != null) part.remove();
        }
        parts.clear();
    }

    // Геттеры
    public Player getOwner() { return owner; }
    public VehicleManager.VehicleType getType() { return type; }
    public int getHealth() { return health; }
    public Location getLocation() { return location; }
}