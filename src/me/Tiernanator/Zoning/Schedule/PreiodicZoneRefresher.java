package me.Tiernanator.Zoning.Schedule;

import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Zoning.ZoningMain;
import me.Tiernanator.Zoning.Zone.Zone;

public class PreiodicZoneRefresher extends BukkitRunnable {

	@SuppressWarnings("unused")
	private static ZoningMain plugin;

	public PreiodicZoneRefresher(ZoningMain main) {
		plugin = main;
	}
	
	//the command that runs periodically that gets a random message and broadcasts it
	@Override
	public void run() {
		
		Zone.initialiseZones();
		
		
	}
	
}
