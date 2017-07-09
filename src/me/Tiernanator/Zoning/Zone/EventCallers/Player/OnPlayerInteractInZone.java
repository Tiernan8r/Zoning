package me.Tiernanator.Zoning.Zone.EventCallers.Player;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Player.PlayerInteractInZoneEvent;

public class OnPlayerInteractInZone implements Listener {

	private static ZoningMain plugin;
	
	public OnPlayerInteractInZone(ZoningMain main) {
		plugin = main;
	}

	@EventHandler
	public void onPlayerBreakBlock(PlayerInteractEvent event) {
		
		if (event.isCancelled()) {
			return;
		}

		List<Zone> allZones = Zone.allZones();
		
		if(allZones == null) {
			return;
		}
		Block block = event.getClickedBlock();
		
		World playerWorld = block.getWorld();

		for (Zone zone : allZones) {

			Location centre = zone.getCentre();
			
			if(centre == null) {
				continue;
			}
			
			World world = centre.getWorld();
			
			if(!(world.equals(playerWorld))) {
				continue;
			}
			
			Player player = event.getPlayer();

			boolean inLocation = zone.isInZone(block);
			
			if (inLocation) {
				
				BlockFace blockFace = event.getBlockFace();
				Action action = event.getAction();
				EquipmentSlot hand = event.getHand();
				ItemStack item = event.getItem();
				
				PlayerInteractInZoneEvent playerInteractInZoneEvent = new PlayerInteractInZoneEvent(zone, player, block, blockFace, hand, action, item);
				plugin.getServer().getPluginManager().callEvent(playerInteractInZoneEvent);
				event.setCancelled(playerInteractInZoneEvent.isCancelled());
				return;
			}
		}

	}
}
