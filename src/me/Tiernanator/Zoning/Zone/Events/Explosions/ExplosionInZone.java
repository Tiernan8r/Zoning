package me.Tiernanator.Zoning.Zone.Events.Explosions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Zoning.Main;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Explosions.ExplosionInZoneEvent;

public class ExplosionInZone implements Listener {

	public static Main plugin;

	public ExplosionInZone(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onTNTExplodeNearZone(ExplosionInZoneEvent event) {

		if (event.isCancelled()) {
			return;
		}

		Location location = event.getExplosionLocation();
		Zone zone = event.getZone();
		Location centre = zone.getCentre();

		int radius = zone.getRadius() + 8;

		int distance = (int) Math.ceil(centre.distance(location));

		boolean inLocation = distance <= radius;

		if (inLocation) {

			Entity entity = event.getEntity();

			if (entity.getType() == EntityType.CREEPER) {

				Creeper creeper = (Creeper) entity;
				creeper.remove();

//				World world = entity.getWorld();
//
//				world.spawnParticle(Particle.EXPLOSION_HUGE, location, 1);
//				world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 5);
//				world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
			}
			World world = entity.getWorld();
			
			world.spawnParticle(Particle.EXPLOSION_HUGE, location, 1);
			world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 5);
			world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);

			event.setCancelled(true);
		}
	}
}
