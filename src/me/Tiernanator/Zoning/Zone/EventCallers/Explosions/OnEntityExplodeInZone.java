package me.Tiernanator.Zoning.Zone.EventCallers.Explosions;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import me.Tiernanator.Zoning.Main;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Explosions.ExplosionInZoneEvent;

public class OnEntityExplodeInZone implements Listener {

	public static Main plugin;

	public OnEntityExplodeInZone(Main main) {
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
//				if (entity.getType() == EntityType.CREEPER) {
//					
//					Creeper creeper = (Creeper) entity;
//					creeper.remove();
//					
//					world = entity.getWorld();
//					
//					world.spawnParticle(Particle.EXPLOSION_HUGE, location, 1);
//					world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 5);
//					world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
//				}
//				
//				event.setCancelled(true);
				ExplosionInZoneEvent explosionInZoneEvent = new ExplosionInZoneEvent(zone, location, radius, entity);
				plugin.getServer().getPluginManager().callEvent(explosionInZoneEvent);
				event.setCancelled(explosionInZoneEvent.isCancelled());
				return;
			}
		}
	}
}
