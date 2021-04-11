package me.Tiernanator.Zoning.Zone.EventCallers.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Player.PlayerEnterZoneEvent;
import me.Tiernanator.Zoning.Zone.CustomEvents.Player.PlayerLeaveZoneEvent;

public class OnPlayerEnterLeaveZone implements Listener {

	 private static ZoningMain plugin;
	private HashMap<Player, List<Location>> previousLocations = new HashMap<Player, List<Location>>();
	private static HashMap<Player, HashMap<Zone, Boolean>> playerInZone = new HashMap<Player, HashMap<Zone, Boolean>>();

	private int arraySize = 5;

	public OnPlayerEnterLeaveZone(ZoningMain main) {
		 plugin = main;
	}

	private List<Location> getPreviousPlayerLocations(Player player) {
		if (previousLocations.containsKey(player)) {
			return previousLocations.get(player);
		} else {
			return new ArrayList<Location>();
		}
	}

	private static HashMap<Zone, Boolean> getPlayerInZones(Player player) {
		
		if (playerInZone.containsKey(player)) {
			return playerInZone.get(player);
		} else {
			HashMap<Zone, Boolean> toReturn = new HashMap<Zone, Boolean>();
			for(Zone zone : Zone.allZones()) {
				toReturn.put(zone, false);
			}
			return toReturn;
		}
	}

	private void addPlayerLocation(Player player, Location location) {

		List<Location> playerPreviousLocations = getPreviousPlayerLocations(
				player);
		if (playerPreviousLocations.size() > arraySize) {
			playerPreviousLocations.remove(0);
		}
		if (!containsLocation(player, location)) {
			playerPreviousLocations.add(location);
		}
		previousLocations.remove(player);
		previousLocations.put(player, playerPreviousLocations);
	}

	private static void addPlayerInZones(Player player, HashMap<Zone, Boolean> inZones) {
		playerInZone.remove(player);
		playerInZone.put(player, inZones);
	}

	public static void addPlayerInSpecificZones(Player player, Zone zone, boolean inZone) {
		
		HashMap<Zone, Boolean> inZones = getPlayerInZones(player);
		inZones.remove(zone);
		inZones.put(zone, inZone);
		addPlayerInZones(player, inZones);
	}
	
	private boolean containsLocation(Player player, Location location) {

		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		World world = location.getWorld();

		Location locationToAdd = new Location(world, x, y, z);

		List<Location> previousLocations = getPreviousPlayerLocations(player);
		for (Location previousLocation : previousLocations) {

			int x1 = previousLocation.getBlockX();
			int y1 = previousLocation.getBlockY();
			int z1 = previousLocation.getBlockZ();
			World thisWorld = previousLocation.getWorld();

			Location loc = new Location(thisWorld, x1, y1, z1);
			if (loc.equals(locationToAdd)) {
				return true;
			}

		}
		return false;

	}

	@EventHandler
	public void onPlayerEnterZone(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		List<Zone> allZones = Zone.allZones();

		if (allZones == null) {
			return;
		}
		Location playerLocation = player.getLocation();
		World playerWorld = playerLocation.getWorld();
		
		addPlayerLocation(player, playerLocation);
		
		List<Location> previousLocations = new ArrayList<Location>();
		previousLocations = getPreviousPlayerLocations(player);
		
		HashMap<Zone, Boolean> inZones = getPlayerInZones(player);
		
		if(previousLocations.size() < arraySize) {
			return;
		}
		
		int i = 0;
		int j = 0;
		for(Zone zone : allZones) {
			
			i = 0;
			j = 0;
			
			World zoneWorld = zone.getWorld();
			if(!playerWorld.equals(zoneWorld)) {
				continue;
			}
			
			for(Location location : previousLocations) {
				
				if(zone.isInZone(location)) {
					i++;
				} else {
					j++;
				}
				
			}
			if(!inZones.containsKey(zone)) {
				continue;
			}
			boolean inZone = inZones.get(zone);
			
			if(i >= previousLocations.size() && !inZone) {
				
				inZones.remove(zone);
				inZones.put(zone, true);
				
				addPlayerInZones(player, inZones);
				
				PlayerEnterZoneEvent playerEnterZoneEvent = new PlayerEnterZoneEvent(zone, player);
				plugin.getServer().getPluginManager().callEvent(playerEnterZoneEvent);
				event.setCancelled(playerEnterZoneEvent.isCancelled());
				
				break;
				
			} else if(j >= previousLocations.size() && inZone) {
				
				inZones.remove(zone);
				inZones.put(zone, false);
				addPlayerInZones(player, inZones);
				
				PlayerLeaveZoneEvent playerLeaveZoneEvent = new PlayerLeaveZoneEvent(zone, player);
				plugin.getServer().getPluginManager().callEvent(playerLeaveZoneEvent);
				event.setCancelled(playerLeaveZoneEvent.isCancelled());
				break;
				
			}
		}
	}

}
