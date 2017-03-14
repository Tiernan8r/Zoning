package me.Tiernanator.Zoning.Zone.Events.Fire;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Zoning.Main;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Fire.FireSpreadInZoneEvent;

public class FireSpreadInZone implements Listener {

	public static Main plugin;

	public FireSpreadInZone(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onFireSpread(FireSpreadInZoneEvent event) {

		if (event.isCancelled()) {
			return;
		}
		Block fireBlock = event.getFireSource();
		if (!(fireBlock.getType() == Material.FIRE)) {
			return;
		}

		Zone zone = event.getZone();
		Block block = event.getBlockSpreadTo();

		boolean inLocation = zone.isInZone(block);

		if (inLocation) {

			event.setCancelled(true);

		}
	}
}
