package me.Tiernanator.Zoning;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceRegulator implements Listener {

	@SuppressWarnings("unused")
	private ZoningMain plugin;

	public BlockPlaceRegulator(ZoningMain main) {
		plugin = main;
	}

	@EventHandler
	public void onPlayerPlaceForbiddenBlock(BlockPlaceEvent event) {

		if(event.isCancelled()) {
			return;
		}
		
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if (block.getType() == Material.BEDROCK) {
			if (player.hasPermission("place.bedrock")) {
				return;
			}
			event.setCancelled(true);
			player.sendMessage(
					ChatColor.DARK_RED + "No placing of bedrock allowed!!");
			return;
		}
		if (block.getType() == Material.BARRIER) {
			if (player.hasPermission("place.barrier")) {
				return;
			}
			event.setCancelled(true);
			player.sendMessage(
					ChatColor.DARK_RED + "No placing of barriers allowed!!");
			return;
		}
	}

}
