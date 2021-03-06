package me.Tiernanator.Zoning.Zone.CustomEvents.Blocks;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Tiernanator.Zoning.Zone.Zone;

public final class BlockBreakInZoneEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the zone in which the block was places
    private Zone zone;
    //the player who broke
    private Player player;
    //the block broken
    private Block block;
    //Whether the event can continue or not is handled by isCancelled
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public BlockBreakInZoneEvent(Zone zone, Player player, Block block) {
        this.zone = zone;
        this.player = player;
        this.block = block;
        
    }

    //return the zone in which the block was broken
    public Zone getZone() {
        return this.zone;
    }
    //return the block places
    public Block getBlock() {
        return this.block;
    }
    //get the player who done it
    public Player getPlayer() {
        return player;
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