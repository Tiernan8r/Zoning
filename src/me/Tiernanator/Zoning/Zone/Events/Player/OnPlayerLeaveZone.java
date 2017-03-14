package me.Tiernanator.Zoning.Zone.Events.Player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Packets.Titler.PlayerTitler;
import me.Tiernanator.Zoning.Main;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.CustomEvents.Player.PlayerLeaveZoneEvent;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;

public class OnPlayerLeaveZone implements Listener {

	private int fadeInTicks = 20;
	private int stayTicks = 40;
	private int fadeOutTicks = 30;

	public OnPlayerLeaveZone(Main main) {
	}

	@EventHandler
	public void onPlayerEnterZone(PlayerLeaveZoneEvent event) {

		Player player = event.getPlayer();
		Zone zone = event.getZone();
		String zoneName = zone.getDisplayName();
		PlayerTitler.playerTitle(player, "You have left: ", true, false, false,
				ChatColor.GOLD, fadeInTicks, stayTicks, fadeOutTicks,
				EnumTitleAction.TITLE);
		PlayerTitler.playerTitle(player, zoneName, true, false, true,
				ChatColor.YELLOW, fadeInTicks, stayTicks, fadeOutTicks,
				EnumTitleAction.SUBTITLE);

	}

}
