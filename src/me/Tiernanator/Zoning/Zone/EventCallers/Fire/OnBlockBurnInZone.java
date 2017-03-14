package me.Tiernanator.Zoning.Zone.EventCallers.Fire;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

import me.Tiernanator.Zoning.Main;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Fire.BlockBurnInZoneEvent;

public class OnBlockBurnInZone implements Listener {

	public static Main plugin;

	public OnBlockBurnInZone(Main main) {
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
				//
				// Location blockLocation = event.getBlock().getLocation();
				// double x = blockLocation.getX();
				// double y = blockLocation.getY();
				// double z = blockLocation.getZ();
				//
				// for (double i = (x - 1); i < (x + 1); i += 1) {
				// for (double j = (y - 1); j < (y + 1); j += 1) {
				// for (double k = (z - 1); k < (z + 1); k += 1) {
				// Location currentLocation = new Location(
				// blockLocation.getWorld(), i, j, k);
				// Block currentBlock = currentLocation.getBlock();
				// if (currentBlock.getType() == Material.FIRE) {
				// currentBlock.setType(Material.AIR);
				// }
				// }
				// }
				// }
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
