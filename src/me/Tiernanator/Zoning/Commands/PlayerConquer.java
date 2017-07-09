package me.Tiernanator.Zoning.Commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Factions.Factions.Faction;
import me.Tiernanator.Factions.Factions.FactionAccessor;
import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.ZoneAccessor;

public class PlayerConquer implements CommandExecutor {

	@SuppressWarnings("unused")
	private ZoningMain plugin;
	ChatColor warning = Colour.WARNING.getColour();
	ChatColor informative = Colour.INFORMATIVE.getColour();
	ChatColor highlight = Colour.HIGHLIGHT.getColour();
	ChatColor good = Colour.GOOD.getColour();
	ChatColor regal = Colour.REGAL.getColour();
	ChatColor bad = Colour.BAD.getColour();

	public PlayerConquer(ZoningMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return false;
		} 
		
		Player player = (Player) sender;
		FactionAccessor factionAccessor = new FactionAccessor(player);
		Faction playerFaction = factionAccessor.getPlayerFaction();
		
		List<Faction> allFactions = Faction.allFactions();
		
		Faction guestFaction = null;
//		Faction rogueFaction = null;
//		
		for(Faction faction : allFactions) {
			if(faction.getName().equalsIgnoreCase("guest")) {
				guestFaction = faction;
//			} else if(faction.getName().equalsIgnoreCase("rogue")) {
//				rogueFaction = faction;
			}
		}
		
		if(playerFaction.getName().equalsIgnoreCase(guestFaction.getName())) {
			player.sendMessage(bad + "You must join a faction before you can conquer a zone.");
			return false;
		}
		
		Location playerLocation = player.getLocation();
		
//		if(playerFaction.equals(rogueFaction)) {
//			
//			for(Faction faction : allFactions) {
//				
//				if (faction.getName().equalsIgnoreCase("guest") || faction.getName().equalsIgnoreCase("rogue")) {
//					continue;
//				}
//				
//				Location centre = CoreZone.getZoneCentre(faction.getName());
//
//				if(centre == null || centre.equals(new Location(player.getWorld(), 0.5, 0, 0.5))) {
//					continue;
//				}
//				
//				int distance = (int) Math.ceil(centre.distance(playerLocation));
//				
//				int radius = ZoneRadiusCalculator.calculateCoreZoneRadius(10) + ZoneRadiusCalculator.calculateSubZoneRadius(playerFaction.getNumberOfSubfactions());
//				
//				if(distance <= radius) {
//					player.sendMessage(warning + "You are in the " + highlight + faction.getName() + warning + "'s zone, as a Rogue, you must conquer a zone outside of the faction zones.");
//					return false;
//				}
//			}
//			
//		} else {
//			
//			Location centre = CoreZone.getZoneCentre(playerFaction.getName());
//			
//			if(!(centre == null) || !(centre.equals(new Location(player.getWorld(), 0.5, 0, 0.5)))) {
//				int radius = ZoneRadiusCalculator.calculateCoreZoneRadius(10);
//				
//				int distance = (int) Math.ceil(centre.distance(playerLocation));
//				
//				if(distance >= radius) {
//					player.sendMessage(warning + "You must be within your own faction zone in order to conquer a zone.");
//					return false;
//				}
//			}
//			
//		}
		
		List<Zone> allZones = Zone.allZones();
		
		if(!(allZones == null)) {
			
			for(Zone zone : allZones) {
				
				Location centre = zone.getCentre();
//				int factionMaxPower = playerFaction.getNumberOfSubfactions();
//				int radius = ZoneRadiusCalculator.calculateSubZoneRadius(factionMaxPower) * 2;
				int radius = 25;
				int distance = (int) Math.ceil(centre.distance(playerLocation));
				
//				String playerUUID = player.getUniqueId().toString();
//				List<String> zoneOwners = zone.getOwnerUUIDs();
				
				if(zone.canBuild(player)) {
					continue;
				}
				
				if(distance <= radius) {
					
					String zoneName = zone.getDisplayName();
					player.sendMessage(warning + "You are within the Zone " + highlight + zoneName + warning + ", you must be outside of all players' zones to conquer your own zone.");
					return false;
					
				}
			}
			
		}
		
		boolean override = false;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase("override")) {
				override = true;
			}
		}
		
		String playerUUID = player.getUniqueId().toString();
		boolean hasZone = ZoneAccessor.isZone(playerUUID);
		
		if(hasZone && !override) {
			
			List<Zone> memberZones = Zone.getZones(playerUUID);
			for(Zone zone : memberZones) {
				if(zone.isShared()) {
					continue;
				}
				
				Location centre = zone.getCentre();
				String x = Double.toString(centre.getBlockX() + 0.5);
				String y = Double.toString(centre.getBlockY());
				String z = Double.toString(centre.getBlockZ() + 0.5);

				player.sendMessage(warning + "You have already conquered a zone at " + regal + x + warning + ", " + regal + y + warning + ", " + regal + z + warning + " to change it's location, append the value " + highlight + "'override'" + warning + " to the command.");
				return false;
				
			}
//			Location centre = zone.getZoneCentre(player.getUniqueId().toString());
//			String x = Double.toString(centre.getBlockX() + 0.5);
//			String y = Double.toString(centre.getBlockY());
//			String z = Double.toString(centre.getBlockZ() + 0.5);
//
//			player.sendMessage(warning + "You have already conquered a zone at " + regal + x + warning + ", " + regal + y + warning + ", " + regal + z + warning + " to change it's location, append the value " + highlight + "'override'" + warning + " to the command.");
//			return false;
			
		}
		
		Location centre = playerLocation;
//		int playerPower = Power.getPlayerPower(player);
//		int radius = ZoneRadiusCalculator.calculateSubZoneRadius(playerPower);
		int radius = 25;
		
		String displayName = null;
		List<Zone> memberZones = Zone.getZones(playerUUID);
		for(Zone zone : memberZones) {
			if(zone.isShared()) {
				continue;
			}
			displayName = zone.getDisplayName();
		}
//		if(hasZone) {
//			displayName = SubZone.getZoneDisplayName(playerUUID);
//		} else {
//			displayName = player.getName();
//		}
		if(displayName == null) {
			displayName = player.getName();
		}
		String owner = player.getUniqueId().toString();
		
		Zone zone = new Zone(owner, displayName, centre, radius, owner, false);
	
		Zone.addZone(zone);
//		SubZone.addZone(playerUUID, displayName, centre, radius, owner);
		
		
		String x = Double.toString(centre.getBlockX() + 0.5);
		String y = Double.toString(centre.getBlockY());
		String z = Double.toString(centre.getBlockZ() + 0.5);
		
		if(!override) {
			player.sendMessage(
					good + "Your new zone has been registered with a centre of "
							+ regal + x + good + " ,"
							+ regal + y + good + " ,"
							+ regal + z);
		} else {
			
			player.sendMessage(good + "Your zone has been moved to " + regal
					+ x + good + " ," + regal
					+ y + good + " ," + regal
					+ z);
		}
		
		return false;
	}
	
}
