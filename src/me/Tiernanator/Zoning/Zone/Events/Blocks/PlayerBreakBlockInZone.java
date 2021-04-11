package me.Tiernanator.Zoning.Zone.Events.Blocks;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Players.PlayerLogger;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Blocks.BlockBreakInZoneEvent;

public class PlayerBreakBlockInZone implements Listener {

	ChatColor warning = Colour.WARNING.getColour();
	ChatColor highlight = Colour.HIGHLIGHT.getColour();
	ChatColor informative = Colour.INFORMATIVE.getColour();

	public PlayerBreakBlockInZone() { 
	}

	@EventHandler
	public void onPlayerBreakBlock(BlockBreakInZoneEvent event) {

		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		Zone zone = event.getZone();

		if (zone.canBuild(player)) {
			return;
		}

		List<String> ownerNames = zone.getOwnerNames();
		String zoneName = zone.getDisplayName();

		zoneName = PlayerLogger.getPlayerNameByUUID(zoneName);
		if (zoneName == null) {
			zoneName = zone.getDisplayName();
			zoneName = zoneName.replaceAll("_", " ");
		}

		player.sendMessage(warning + "The zone " + informative + zoneName
				+ warning + " belongs to: ");
		for (String ownerName : ownerNames) {
			player.sendMessage(highlight + " - " + ownerName);
		}
		player.sendMessage(warning + " and you can't build here!");

		event.setCancelled(true);
		return;

	}
}
