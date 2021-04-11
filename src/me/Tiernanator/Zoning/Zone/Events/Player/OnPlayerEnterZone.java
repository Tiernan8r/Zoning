package me.Tiernanator.Zoning.Zone.Events.Player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Utilities.Packets.Titler.PlayerTitler;
import me.Tiernanator.Utilities.Packets.Titler.TitleAction;
import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Player.PlayerEnterZoneEvent;

public class OnPlayerEnterZone implements Listener {

	private int fadeInTicks = 20;
	private int stayTicks = 40;
	private int fadeOutTicks = 30;
	
	public OnPlayerEnterZone(ZoningMain main) {
	}

	@EventHandler
	public void onPlayerEnterZone(PlayerEnterZoneEvent event) {

		Player player = event.getPlayer();
		Zone zone = event.getZone();
		String zoneName = zone.getDisplayName();
		zoneName = zoneName.replaceAll("_", " ");
		PlayerTitler.playerTitle(player, "Welcome to: ", true, false, false,
				ChatColor.DARK_AQUA, fadeInTicks, stayTicks, fadeOutTicks,
				TitleAction.TITLE);
		PlayerTitler.playerTitle(player, zoneName, true, false, true,
				ChatColor.AQUA, fadeInTicks, stayTicks, fadeOutTicks,
				TitleAction.SUBTITLE);

	}

}
