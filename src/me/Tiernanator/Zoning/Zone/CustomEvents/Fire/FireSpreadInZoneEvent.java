package me.Tiernanator.Zoning.Zone.CustomEvents.Fire;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Tiernanator.Zoning.Zone.Zone;

//This is the custom custom menu click event that is called by OnPlayerMenuClick, it just contains functions that
//return all the values needed

public final class FireSpreadInZoneEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the zone in which the block was places
    private Zone zone;
    //the source of the fire
    private Block from;
    //Where the fire is spreading to
    private Block to;
    //Whether the event can continue or not is handled by isCancelled
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public FireSpreadInZoneEvent(Zone zone, Block fireSource, Block spreadTo) {
        this.zone = zone;
        this.from = fireSource;
        this.to = spreadTo;
    }

    //return the zone in which the block was placed
    public Zone getZone() {
        return this.zone;
    }
    //return the fire source
    public Block getFireSource() {
        return this.from;
    }
    //return the block the fire spread to
    public Block getBlockSpreadTo() {
    	return this.to;
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