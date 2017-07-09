package me.Tiernanator.Zoning.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.ZoneAccessor;

public class NameMyZone implements CommandExecutor {

	@SuppressWarnings("unused")
	private ZoningMain plugin;
	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public NameMyZone(ZoningMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return false;
		}

		if (args.length < 1) {
			sender.sendMessage(warning + "Please specify a name for you zone.");
			return false;
		}

		Player player = (Player) sender;

		List<Zone> allZones = new ArrayList<Zone>();
		allZones = Zone.allZones();

		if (allZones == null) {
			sender.sendMessage(bad + "There are no zones for you to rename.");
			return false;
		}

		for (Zone individualZone : allZones) {

			// List<String> ownerUUIDs = individualZone.getOwnerUUIDs();
			//
			// String playerUUID = player.getUniqueId().toString();
			//
			// if(zoneOwner.equals(playerUUID)) {
			//
			// SubZone.setZoneDisplayName(individualZone, zoneName);
			//
			// player.sendMessage(good + "Your zone has been renamed to: " +
			// informative + ZoneName.parseZoneCodeToName(zoneName));
			//
			// return true;
			// }
			if (individualZone.canBuild(player)) {
				if (!individualZone.isShared()) {

					String displayName = "";
					for (int i = 0; i < args.length; i++) {
						displayName += args[i];
						if (!(i == (args.length - 1))) {
							displayName += " ";
						}
					}

					String zoneName = individualZone.getName();
					ZoneAccessor.setZoneDisplayName(zoneName, displayName);

					player.sendMessage(good + "Your zone has been renamed to: "
							+ informative
							+ displayName);

					return true;

				}
			}
		}

		sender.sendMessage(bad + "It appears that you don't have a zone...");
		return false;

	}
}
