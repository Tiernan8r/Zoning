package me.Tiernanator.Zoning.Zone.EventCallers.Blocks;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.Tiernanator.Zoning.Main;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Blocks.BlockPlaceInZoneEvent;

public class OnPlayerPlaceBlockInZone implements Listener {

//	private ChatColor warning = Colour.WARNING.getColour();
//	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
//	private ChatColor informative = Colour.INFORMATIVE.getColour();

	private static Main plugin;
	
	public OnPlayerPlaceBlockInZone(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent event) {
		
		if (event.isCancelled()) {
			return;
		}

		List<Zone> allZones = Zone.allZones();
		
		if(allZones == null) {
			return;
		}
		
		World playerWorld = event.getBlock().getWorld();

		for (Zone zone : allZones) {

			Location centre = zone.getCentre();
			
			if(centre == null) {
				return;
			}
			
			World world = centre.getWorld();
			
			if(!(world.equals(playerWorld))) {
				continue;
			}
			
			Player player = event.getPlayer();

			Block block = event.getBlock();

			boolean inLocation = zone.isInZone(block);
			
			if (inLocation) {
				
//				if(zone.canBuild(player)) {
//					return;
//				}
//
//				PlayerLogger playerLogger = new PlayerLogger();
//				List<String> ownerNames = zone.getOwnerNames();
//				String zoneName = zone.getDisplayName();
//				
//				zoneName = playerLogger.getPlayerNameByUUID(zoneName);
//				if(zoneName == null) {
//					zoneName = zone.getDisplayName();
//					zoneName = zoneName.replaceAll("_", " ");
//				}
//				
//				player.sendMessage(warning + "The zone " + informative + zoneName + warning + " belongs to: ");
//				for(String ownerName : ownerNames) {
//					player.sendMessage(highlight + " - " + ownerName);
//				}
//				player.sendMessage(warning + " and you can't build here!");
//
//				event.setCancelled(true);
//				return;
				BlockPlaceInZoneEvent blockPlaceInZoneEvent = new BlockPlaceInZoneEvent(zone, player, block);
				plugin.getServer().getPluginManager().callEvent(blockPlaceInZoneEvent);
				event.setCancelled(blockPlaceInZoneEvent.isCancelled());
				return;
				
			}
		}
	}
}
