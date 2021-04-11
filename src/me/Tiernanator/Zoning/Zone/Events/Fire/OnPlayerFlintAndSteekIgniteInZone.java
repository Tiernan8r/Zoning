package me.Tiernanator.Zoning.Zone.Events.Fire;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Player.PlayerInteractInZoneEvent;

public class OnPlayerFlintAndSteekIgniteInZone implements Listener {

	@SuppressWarnings("unused")
	private static ZoningMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	
	public OnPlayerFlintAndSteekIgniteInZone(ZoningMain main) {
		plugin = main;
	}

	@EventHandler
	public void onPlayerIgniteTntInZone(PlayerInteractInZoneEvent event) {

		if (event.isCancelled()) {
			return;
		}

		Block block = event.getClickedBlock();
		if(block.getType() != Material.TNT) {
			return;
		}
		ItemStack item = event.getItemUsed();
		if(item == null) {
			return;
		}
		if(item.getType() != Material.FLINT_AND_STEEL) {
			return;
		}
		Player player = event.getPlayer();
		Zone zone = event.getZone();
		if(zone.canBuild(player)) {
			return;
		}
		
		player.sendMessage(warning + "You cannot ignote TnT in the zone: " + informative + zone.getDisplayName() + warning + ".");
		
		event.setCancelled(true);
	}
}
