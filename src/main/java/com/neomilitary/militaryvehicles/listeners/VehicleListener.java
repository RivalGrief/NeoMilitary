package com.neomilitary.militaryvehicles.listeners;

import com.neomilitary.militaryvehicles.MilitaryVehiclesPlugin;
import com.neomilitary.militaryvehicles.managers.VehicleManager;
import com.neomilitary.militaryvehicles.vehicles.MilitaryVehicle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

public class VehicleListener implements Listener {

    private final VehicleManager vehicleManager;

    public VehicleListener() {
        this.vehicleManager = MilitaryVehiclesPlugin.getInstance().getVehicleManager();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        vehicleManager.removeVehicle(player);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        MilitaryVehicle vehicle = vehicleManager.getPlayerVehicle(player);

        if (vehicle != null && event.getHand() == EquipmentSlot.HAND) {
            // Игрок взаимодействует с техникой
            player.sendMessage("§eУправление техникой: §7WASD - движение, §7Shift - выход");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        MilitaryVehicle vehicle = vehicleManager.getPlayerVehicle(player);

        if (vehicle != null) {
            // Игрок движется с техникой - можно добавить логику движения техники
            vehicle.moveTo(event.getTo());
        }
    }
}