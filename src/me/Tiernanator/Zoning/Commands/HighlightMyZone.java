package me.Tiernanator.Zoning.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.Tiernanator.File.ConfigAccessor;
import me.Tiernanator.Zoning.ZoningMain;

public class HighlightMyZone implements CommandExecutor {

	private ZoningMain plugin;

	public HighlightMyZone(ZoningMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "You can't use this command.");
			return false;
		}
		
		Player player = (Player) sender;
		
		ConfigAccessor locationsAccessor = new ConfigAccessor(plugin,
				"SubZoneLocations.yml");
		
		List<String> zones = new ArrayList<String>();
		zones = locationsAccessor.getConfig().getStringList("All Zones");

		
		for (String individualZone : zones) {

			String ownerPath = "Zone." + individualZone + ".owner";
			String zoneOwner = locationsAccessor.getConfig().getString(ownerPath);
			String playerUUID = player.getUniqueId().toString();
			
			if(zoneOwner.equals(playerUUID)) {
				
				String centrePath = "Zone." + individualZone + ".centre";
				String worldPath = "Zone." + individualZone + ".world";
				String radiusPath = "Zone." + individualZone + ".radius";
				
				List<Integer> centre = locationsAccessor.getConfig().getIntegerList(centrePath);
				String worldName = locationsAccessor.getConfig().getString(worldPath);
				
				int radius = locationsAccessor.getConfig().getInt(radiusPath);
				World world = plugin.getServer().getWorld(worldName);
				double x = centre.get(0);
				double y = player.getLocation().getY();
				double z = centre.get(2);
				
				Location corner1 = new Location(world, x - radius, y , z - radius);
				world.spawnEntity(corner1, EntityType.FIREWORK);
				Location corner2 = new Location(world, x - radius, y , z + radius);
				world.spawnEntity(corner2, EntityType.FIREWORK);
				Location corner3 = new Location(world, x + radius, y , z - radius);
				world.spawnEntity(corner3, EntityType.FIREWORK);
				Location corner4 = new Location(world, x + radius, y , z + radius);
				world.spawnEntity(corner4, EntityType.FIREWORK);
								
				return true;
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "It appears that you don't have a zone...");
				return false;
			}
		}
		return false;
	}
	
}
