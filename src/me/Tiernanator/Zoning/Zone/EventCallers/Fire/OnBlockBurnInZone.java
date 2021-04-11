package me.Tiernanator.Zoning.Zone.EventCallers.Fire;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Fire.BlockBurnInZoneEvent;

public class OnBlockBurnInZone implements Listener {

	public static ZoningMain plugin;

	public OnBlockBurnInZone(ZoningMain main) {
		plugin = main;
	}

	@EventHandler
	public void onBlockBurnInZone(BlockBurnEvent event) {

		if (event.isCancelled()) {
			return;
		}

		List<Zone> allZones = Zone.allZones();

		if (allZones == null) {
			return;
		}

		World fireWorld = event.getBlock().getWorld();

		for (Zone zone : allZones) {

			Location centre = zone.getCentre();

			if (centre == null) {
				continue;
			}

			World world = centre.getWorld();

			if (!(world.equals(fireWorld))) {
				continue;
			}

			Block block = event.getBlock();
			boolean inLocation = zone.isInZone(block);

			if (inLocation) {
				BlockBurnInZoneEvent blockBurnInZoneEvent = new BlockBurnInZoneEvent(
						zone, block);
				plugin.getServer().getPluginManager()
						.callEvent(blockBurnInZoneEvent);;
				event.setCancelled(blockBurnInZoneEvent.isCancelled());
				return;
			}
		}
	}
}
