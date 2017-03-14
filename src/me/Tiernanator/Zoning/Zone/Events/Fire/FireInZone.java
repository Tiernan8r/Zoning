package me.Tiernanator.Zoning.Zone.Events.Fire;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Zoning.Main;
import me.Tiernanator.Zoning.Zone.CustomEvents.Fire.BlockBurnInZoneEvent;

public class FireInZone implements Listener {

	public static Main plugin;

	public FireInZone(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onBlockBurnInZone(BlockBurnInZoneEvent event) {

		if (event.isCancelled()) {
			return;
		}
//
//		Zone zone = event.getZone();
		Block block = event.getBurntBlock();
//		boolean inLocation = zone.isInZone(block);
//
//		if (inLocation) {

			Location blockLocation = block.getLocation();
			double x = blockLocation.getX();
			double y = blockLocation.getY();
			double z = blockLocation.getZ();

			for (double i = (x - 1); i < (x + 1); i += 1) {
				for (double j = (y - 1); j < (y + 1); j += 1) {
					for (double k = (z - 1); k < (z + 1); k += 1) {
						Location currentLocation = new Location(
								blockLocation.getWorld(), i, j, k);
						Block currentBlock = currentLocation.getBlock();
						if (currentBlock.getType() == Material.FIRE) {
							currentBlock.setType(Material.AIR);
						}
					}
				}
			}

			event.setCancelled(true);
//		}
	}
}
