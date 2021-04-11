package me.Tiernanator.Zoning.Zone.EventCallers.Explosions;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Explosions.ExplosionInZoneEvent;

public class OnEntityExplodeInZone implements Listener {

	public static ZoningMain plugin;

	public OnEntityExplodeInZone(ZoningMain main) {
		plugin = main;
	}

	@EventHandler
	public void onTNTExplodeNearZone(ExplosionPrimeEvent event) {
		
		if (event.isCancelled()) {
			return;
		}
		
		List<Zone> allZones = Zone.allZones();

		if (allZones == null) {
			return;
		}
		
		World entityWorld = event.getEntity().getWorld();

		for (Zone zone : allZones) {

			Location centre = zone.getCentre();

			if (centre == null) {
				continue;
			}
			
			World world = centre.getWorld();
			
			if(!(world.equals(entityWorld))) {
				continue;
			}

			Location location = event.getEntity().getLocation();
			
			int radius = zone.getRadius() + 8;

			int distance = (int) Math.ceil(centre.distance(location));

			boolean inLocation = distance <= radius;
			
			if (inLocation) {
				
				Entity entity = event.getEntity();
				ExplosionInZoneEvent explosionInZoneEvent = new ExplosionInZoneEvent(zone, location, radius, entity);
				plugin.getServer().getPluginManager().callEvent(explosionInZoneEvent);
				event.setCancelled(explosionInZoneEvent.isCancelled());
				return;
			}
		}
	}
}
