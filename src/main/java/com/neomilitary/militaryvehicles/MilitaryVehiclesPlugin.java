package com.neomilitary.militaryvehicles;

import com.neomilitary.militaryvehicles.managers.VehicleManager;
import com.neomilitary.militaryvehicles.commands.VehicleCommands;
import com.neomilitary.militaryvehicles.listeners.VehicleListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MilitaryVehiclesPlugin extends JavaPlugin {

    private static MilitaryVehiclesPlugin instance;
    private VehicleManager vehicleManager;

    @Override
    public void onEnable() {
        instance = this;
        this.vehicleManager = new VehicleManager();

        // Сохранение конфига по умолчанию
        saveDefaultConfig();

        // Регистрация команд
        getCommand("tank").setExecutor(new VehicleCommands());
        getCommand("ifv").setExecutor(new VehicleCommands());
        getCommand("helicopter").setExecutor(new VehicleCommands());
        getCommand("removevehicle").setExecutor(new VehicleCommands());
        getCommand("vehiclestats").setExecutor(new VehicleCommands());

        // Регистрация слушателей
        getServer().getPluginManager().registerEvents(new VehicleListener(), this);

        getLogger().info("§a=================================");
        getLogger().info("§aRussian Military Vehicles Enabled!");
        getLogger().info("§aVersion: " + getDescription().getVersion());
        getLogger().info("§a=================================");
    }

    @Override
    public void onDisable() {
        if (vehicleManager != null) {
            vehicleManager.removeAllVehicles();
        }
        getLogger().info("§cRussian Military Vehicles Disabled!");
    }

    public static MilitaryVehiclesPlugin getInstance() {
        return instance;
    }

    public VehicleManager getVehicleManager() {
        return vehicleManager;
    }
}