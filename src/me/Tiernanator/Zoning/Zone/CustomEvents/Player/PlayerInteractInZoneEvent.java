package me.Tiernanator.Zoning.Zone.CustomEvents.Player;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Zoning.Zone.Zone;

public final class PlayerInteractInZoneEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the zone in which the block was places
    private Zone zone;
    //the player who broke
    private Player player;
    //the block clicked
    private Block block;
    //The blockFace clicked
    private BlockFace blockFace;
    //The type of click action
    private Action action;
    //The hand used to click with
    private EquipmentSlot hand;
    //The item used to cick with
    private ItemStack item;
    
    //Whether the event can continue or not is handled by isCancelled
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public PlayerInteractInZoneEvent(Zone zone, Player player, Block block, BlockFace blockFace, EquipmentSlot hand, Action action, ItemStack item) {
        
    	this.zone = zone;
        this.player = player;
        this.block = block;
        this.blockFace = blockFace;
        this.action = action;
        this.hand = hand;
        this.item = item;
        
    }

    //return the zone in which the block was broken
    public Zone getZone() {
        return this.zone;
    }
    //return the block places
    public Block getClickedBlock() {
        return this.block;
    }
    //Get the BlockFace clicked
    public BlockFace getClickedBlockFace() {
    	return this.blockFace;
    }
    //get the player who done it
    public Player getPlayer() {
        return player;
    }
    //Get the Hand used
    public EquipmentSlot getHand() {
    	return this.hand;
    }
    //Get the type of Action used
    public Action getAction() {
    	return this.action;
    }
    //Get the item in hand
    public ItemStack getItemUsed() {
    	return this.item;
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