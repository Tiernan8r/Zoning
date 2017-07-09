package me.Tiernanator.Zoning.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.RelativeLocation;
import me.Tiernanator.Utilities.Players.GetPlayer;
import me.Tiernanator.Utilities.Players.PlayerLogger;
import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.ZoneAccessor;

public class AddZone implements CommandExecutor {

	@SuppressWarnings("unused")
	private ZoningMain plugin;
	ChatColor warning = Colour.WARNING.getColour();
	ChatColor informative = Colour.INFORMATIVE.getColour();
	ChatColor highlight = Colour.HIGHLIGHT.getColour();
	ChatColor good = Colour.GOOD.getColour();
	ChatColor regal = Colour.REGAL.getColour();
	ChatColor bad = Colour.BAD.getColour();

	public AddZone(ZoningMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return false;
		}

		Player player = (Player) sender;
		int firstNumber = args.length + 1;
		for(int i = 0; i < args.length; i++) {
			try {
				if(!args[i].equalsIgnoreCase("~")) {
					Integer.parseInt(args[i]);
				}
				firstNumber = i;
				break;
			}catch (Exception e) {
				continue;
			}
		}
		
		if(firstNumber > args.length) {
			player.sendMessage(bad + "You must provide the " + informative
					+ "name " + bad + ", " + highlight + "x" + bad + ", "
					+ highlight + "y" + bad + ", " + highlight + "z" + bad
					+ " co-ordinates, the " + informative + "radius" + bad + " and any owners of the zone (" + informative
					+ "All" + bad + ", " + informative + "None" + bad
					+ " or any " + informative + "Player's name" + bad + ").");
			return false;
		}
		String zoneName = "";
		for(int i = 0; i < firstNumber; i++) {
			zoneName += args[i];
			if((i + 1) < firstNumber) {
				zoneName += " ";
			}
		}
		
		if(ZoneAccessor.isZone(zoneName)) {
			player.sendMessage(warning + "A zone by the name: " + informative + zoneName + warning + " already exists!");
			return false;
		}
		
		if(firstNumber + 3 > args.length) {
			
			player.sendMessage(bad + "You must provide the " + highlight + "x" + bad + ", "
					+ highlight + "y" + bad + " & " + highlight + "z" + bad
					+ " co-ordinates for the zone.");
			return false;
			
		}
		Location centre = RelativeLocation.getRelativeLocationsFromString(
				player, args[firstNumber], args[firstNumber + 1], args[firstNumber + 2]);
		if (centre == null) {
			player.sendMessage(informative + args[firstNumber] + warning + ", "
					+ informative + args[firstNumber + 1] + warning + ", " + informative
					+ args[firstNumber + 2] + warning + " is not a valid co-ordinate.");
			return false;
		}
		List<Zone> allZones = Zone.allZones();
		if(allZones != null) {
			for(Zone zone : allZones) {
				if(zone.isInZone(centre)) {
					player.sendMessage(warning + "You are in the zone: " + informative + zone.getDisplayName() + warning + ", you must be outside of all Zones to create a new one.");
					return false;
				}
			}
		}
		
		if(firstNumber + 4 > args.length) {
			player.sendMessage(bad + "You must provide the " + informative + "radius" + bad + " for the zone.");
			return false;
		}
		String radiusSize = args[firstNumber + 3];
		int radius;
		try {
			radius = Integer.parseInt(radiusSize);
		} catch (Exception e) {
			player.sendMessage(highlight + radiusSize + warning + " is not a valid number for a radius!");
			return false;
		}
		if(radius == 0) {
			player.sendMessage(bad + "Ther is no point to a zone with zero radius...");
			return false;
		}
		
		List<String> ownerUUIDs = new ArrayList<String>();

		if (args.length > firstNumber + 4) {
			for (int i = firstNumber + 4; i < args.length; i++) {
				String ownerName = args[i];
				if (ownerName.equalsIgnoreCase("All")) {
					ownerUUIDs.clear();
					ownerUUIDs.add("All");
					break;
				} else if (ownerName.equalsIgnoreCase("None")) {
					ownerUUIDs.clear();
					ownerUUIDs.add("None");
					break;
				} else {
					OfflinePlayer offlinePlayer = GetPlayer.getOfflinePlayer(
							ownerName, player, warning, highlight);
					if (offlinePlayer == null) {
						return false;
					}
					String uuid = offlinePlayer.getUniqueId().toString();
					ownerUUIDs.add(uuid);
				}

			}
		} else {
			ownerUUIDs.add("All");
		}
		boolean shared = ownerUUIDs.size() > 1;

		Zone zone = new Zone(zoneName, zoneName, centre, radius, ownerUUIDs,
				shared);
		ZoneAccessor.addZone(zone);

		double x = centre.getX();
		double y = centre.getY();
		double z = centre.getZ();

		player.sendMessage(good + "A new Zone called " + informative + zoneName
				+ good + " has been registered with a centre of " + regal + x
				+ good + " ," + regal + y + good + " ," + regal + z + good
				+ " and radius " + highlight + radius + good
				+ ", it belongs to: ");
		PlayerLogger playerLogger = new PlayerLogger();
		for (String owner : ownerUUIDs) {
			if (owner.equalsIgnoreCase("All")
					|| owner.equalsIgnoreCase("None")) {
				player.sendMessage(highlight + " - [" + owner + "]");
				break;
			} else {
				String ownerName = playerLogger.getPlayerNameByUUID(owner);
				player.sendMessage(highlight + " - " + ownerName);
			}
		}

		return true;
	}

}
