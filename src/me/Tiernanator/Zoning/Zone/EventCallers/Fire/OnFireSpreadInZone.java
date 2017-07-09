package me.Tiernanator.Zoning.Zone.EventCallers.Fire;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Fire.FireSpreadInZoneEvent;

public class OnFireSpreadInZone implements Listener {

	public static ZoningMain plugin;

	public OnFireSpreadInZone(ZoningMain main) {
		plugin = main;
	}

	@EventHandler
	public void onFireSpread(BlockSpreadEvent event) {

		if (event.isCancelled()) {
			return;
		}
		Block fireBlock = event.getSource();
		if (!(fireBlock.getType() == Material.FIRE)) {
			return;
		}

		List<Zone> allZones = Zone.allZones();

		if (allZones == null) {
			return;
		}

		World fireWorld = fireBlock.getWorld();

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

				FireSpreadInZoneEvent fireSpreadInZoneEvent = new FireSpreadInZoneEvent(
						zone, fireBlock, block);
				plugin.getServer().getPluginManager()
						.callEvent(fireSpreadInZoneEvent);
				event.setCancelled(fireSpreadInZoneEvent.isCancelled());
				return;

			}
		}
	}
}
