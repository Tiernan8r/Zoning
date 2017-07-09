package me.Tiernanator.Zoning.Zone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Utilities.Players.GetPlayer;
import me.Tiernanator.Utilities.Players.PlayerLogger;
import me.Tiernanator.Zoning.ZoningMain;

public class Zone {

	private static ZoningMain plugin;
	public static void setPlugin(ZoningMain main) {
		plugin = main;
	}
	
	private static List<Zone> allZones = new ArrayList<Zone>();

	public static List<Zone> allZones() {
		return allZones;
	}
	
	public static void addZone(Zone zone) {
		List<Zone> allZones = new ArrayList<Zone>();
		allZones = allZones();
		if(!allZones.contains(zone)) {
			allZones.add(zone);
		}
		setAllZones(allZones);
	}
	
	public static void setAllZones(List<Zone> newZones) {
		if(newZones == null) {
			return;
		}
		allZones = newZones;
	}

	private Location centre;
	private World world;
	private String name;
	private String displayName;
	private int radius;
	private List<String> ownerUUIDs;
	private boolean shared;

	public Zone(String name, String displayName, Location centre, int radius, List<String> ownerUUIDs, boolean shared) {
		
		this.name = name;
		this.displayName = displayName;
		this.centre = centre;
		this.world = centre.getWorld();
		this.radius = radius;
		this.ownerUUIDs = ownerUUIDs;
		this.shared = shared;
		
		addZone(this);
		
	}
	
	public Zone(String name, String displayName, Location centre, int radius, String ownerUUID, boolean shared) {
		
		List<String> ownerUUIDs = new ArrayList<String>();
		ownerUUIDs.add(ownerUUID);

		this.name = name;
		this.displayName = displayName;
		this.centre = centre;
		this.world = centre.getWorld();
		this.radius = radius;
		this.ownerUUIDs = ownerUUIDs;
		this.shared = shared;
		
		addZone(this);
		ZoneAccessor.addZone(this);
	}
	
	public Location getCentre() {
		return this.centre;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public List<String> getOwnerUUIDs() {
		return this.ownerUUIDs;
	}
 	
	public boolean isShared() {
		return this.shared;
	}
	
	public List<String> getOwnerNames() {
		
		List<String> owners = new ArrayList<String>();
		PlayerLogger playerLogger = new PlayerLogger();
		for(String ownerUUID : getOwnerUUIDs()) {
			if(ownerUUID.equalsIgnoreCase("All")) {
				owners.add("[All]");
				break;
			} else if(ownerUUID.equalsIgnoreCase("None")) {
				owners.add("[None]");
				break;
			}
			String ownerName = playerLogger.getPlayerNameByUUID(ownerUUID);
			if(ownerName == null) {
				continue;
			}
			owners.add(ownerName);
		}
		return owners;
		
	}
	
	public List<OfflinePlayer> getOwners() {
		
		List<OfflinePlayer> owners = new ArrayList<OfflinePlayer>();
		for(String ownerName : getOwnerNames()) {
			OfflinePlayer player = GetPlayer.getOfflinePlayer(ownerName);
			if(player == null) {
				continue;
			}
			owners.add(player);
		}
		return owners;
		
	}
	
	public boolean isInZone(Location location) {
		
		Location centre = getCentre();
		
		World locationWorld = location.getWorld();
		World zoneWorld = getWorld();
		
		if(!locationWorld.equals(zoneWorld)) {
			return false;
		}
		
		int radius = getRadius();
		
		int distance = (int) Math.ceil(centre.distance(location));

		boolean inLocation = distance <= radius;
		
		return inLocation;
	}
	
	public boolean isInZone(Block block) {
		return isInZone(block.getLocation());
	}
	
	public boolean canBuild(Player player) {
		
		List<String> owners = getOwnerUUIDs();
		String playerUUID = player.getUniqueId().toString();
		for(String owner : owners) {
			
			if (owner.equalsIgnoreCase("All")) {
				return true;
			} else if (owner.equalsIgnoreCase("None")) {
				return false;
			} else if (playerUUID.equals(owner)) {
				return true;
			}
			
		}
		return false;
	}
	
	public static Zone getZone(String name) {
		List<Zone> allZones = allZones();
		for(Zone zone : allZones) {
			String zoneName = zone.getName();
			if(zoneName.equalsIgnoreCase(name)) {
				return zone;
			}
		}
		return null;
	}
	
	public static Zone getZone(Location location) {
		
		List<Zone> allZones = allZones();
		for(Zone zone : allZones) {
			if(zone.isInZone(location)) {
				return zone;
			}
		}
		return null;
		
	}
	
	public static Zone getZone(Block block) {
		Location location = block.getLocation();
		return getZone(location);
	}
	
	public static List<Zone> getZones(String playerUUID) {
		
		List<Zone> allZones = allZones();
		List<Zone> memberZones = new ArrayList<Zone>();
		
		if(allZones == null) {
			return null;
		}
		for(Zone zone : allZones) {
			List<String> ownerUUIDs = zone.getOwnerUUIDs();
			if(ownerUUIDs == null) {
				continue;
			}
			if(ownerUUIDs.contains(playerUUID)) {
				memberZones.add(zone);
			}
		}
		return memberZones;
	}
	
	public static Zone getPersonnelZone(String playerUUID) {
		
		List<Zone> playerZones = getZones(playerUUID);
		for(Zone zone : playerZones) {
			if(!zone.isShared()) {
				return zone;
			}
		}
		return null;
	}
	
	public static void initialiseZones() {
		
		List<Zone> zones = new ArrayList<Zone>();
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				List<String> allZoneNames = ZoneAccessor.allZones();
				if(allZoneNames == null || allZoneNames.isEmpty()) {
					return;
				}
				for(String zoneName : allZoneNames) {
					
					String displayName = ZoneAccessor.getZoneDisplayName(zoneName);
					Location centre = ZoneAccessor.getZoneCentre(zoneName);
					int radius = ZoneAccessor.getZoneRadius(zoneName);
					List<String> ownerUUIDs = ZoneAccessor.getZoneOwners(zoneName);
					boolean shared = ZoneAccessor.isShared(zoneName);
					
					Zone zone = new Zone(zoneName, displayName, centre, radius, ownerUUIDs, shared);
					zones.add(zone);
				}
				
			}
		};
		runnable.runTaskAsynchronously(plugin);
		allZones = zones;
		
	}
	
}
