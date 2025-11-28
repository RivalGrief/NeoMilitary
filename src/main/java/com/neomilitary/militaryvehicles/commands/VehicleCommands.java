package com.neomilitary.militaryvehicles.commands;

import com.neomilitary.militaryvehicles.MilitaryVehiclesPlugin;
import com.neomilitary.militaryvehicles.managers.VehicleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VehicleCommands implements CommandExecutor {

    private final VehicleManager vehicleManager;

    public VehicleCommands() {
        this.vehicleManager = MilitaryVehiclesPlugin.getInstance().getVehicleManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭта команда только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        String cmd = command.getName().toLowerCase();

        switch (cmd) {
            case "tank":
                return spawnTank(player, args);
            case "ifv":
                return spawnIFV(player, args);
            case "helicopter":
                return spawnHelicopter(player, args);
            case "apc":
                return spawnAPC(player, args);
            case "removevehicle":
                vehicleManager.removeVehicle(player);
                return true;
            case "vehiclestats":
                return showVehicleStats(player);
            default:
                return false;
        }
    }

    private boolean spawnTank(Player player, String[] args) {
        VehicleManager.UnitMarking marking = parseMarking(args);
        return vehicleManager.spawnVehicle(player, VehicleManager.VehicleType.T72_TANK,
                player.getLocation(), marking);
    }

    private boolean spawnIFV(Player player, String[] args) {
        VehicleManager.UnitMarking marking = parseMarking(args);
        return vehicleManager.spawnVehicle(player, VehicleManager.VehicleType.BMP2_IFV,
                player.getLocation(), marking);
    }

    private boolean spawnHelicopter(Player player, String[] args) {
        VehicleManager.UnitMarking marking = parseMarking(args);
        return vehicleManager.spawnVehicle(player, VehicleManager.VehicleType.MI24_HELICOPTER,
                player.getLocation(), marking);
    }

    private boolean spawnAPC(Player player, String[] args) {
        VehicleManager.UnitMarking marking = parseMarking(args);
        return vehicleManager.spawnVehicle(player, VehicleManager.VehicleType.KURGANETS_APC,
                player.getLocation(), marking);
    }

    private boolean showVehicleStats(Player player) {
        if (vehicleManager.hasVehicle(player)) {
            player.sendMessage("§aУ вас есть активная техника");
        } else {
            player.sendMessage("§cУ вас нет активной техники");
        }
        return true;
    }

    private VehicleManager.UnitMarking parseMarking(String[] args) {
        if (args.length > 0) {
            return VehicleManager.UnitMarking.fromString(args[0]);
        }
        return VehicleManager.UnitMarking.NONE;
    }
}