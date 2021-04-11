package me.Tiernanator.Zoning.Zone.CustomEvents.Explosions;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Tiernanator.Zoning.Zone.Zone;

//This is the custom custom menu click event that is called by OnPlayerMenuClick, it just contains functions that
//return all the values needed

public final class ExplosionInZoneEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the zone in which the block was placed
    private Zone zone;
    //the location of the explosion
    private Location explosionLocation;
    //The radius of the explosion
    private float radius;
    //If not null, the entity that caused the explosion
    private Entity entity;
    //Whether the event can continue or not is handled by isCancelled
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public ExplosionInZoneEvent(Zone zone, Location explosionLocation, float explosionRadius, Entity entity) {
        this.zone = zone;
        this.explosionLocation = explosionLocation;
        this.radius = explosionRadius;
        this.entity = entity;
    }

    //return the zone in which the block was placed
    public Zone getZone() {
        return this.zone;
    }
    //return the block placed
    public Location getExplosionLocation() {
        return this.explosionLocation;
    }
    //return the radius of the explosion
    public float getExplosionRadius() {
    	return this.radius;
    }
    //Return the entity that caused the explosion
    public Entity getEntity() {
    	return this.entity;
    }
    
    //the next two are necessary for the server to use the event
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    //Just for cancelling the event
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		isCancelled = cancel;
		
	}
}