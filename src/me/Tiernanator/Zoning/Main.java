package me.Tiernanator.Zoning;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Tiernanator.SQL.SQLServer;
import me.Tiernanator.SQL.MySQL.MySQL;
import me.Tiernanator.Zoning.Commands.AddZone;
import me.Tiernanator.Zoning.Commands.NameMyZone;
import me.Tiernanator.Zoning.Commands.PlayerConquer;
import me.Tiernanator.Zoning.Schedule.PreiodicZoneRefresher;
import me.Tiernanator.Zoning.Zone.Zone;
import me.Tiernanator.Zoning.Zone.ZoneAccessor;
import me.Tiernanator.Zoning.Zone.EventCallers.Blocks.OnPlayerBreakBlockInZone;
import me.Tiernanator.Zoning.Zone.EventCallers.Blocks.OnPlayerPlaceBlockInZone;
import me.Tiernanator.Zoning.Zone.EventCallers.Explosions.OnEntityExplodeInZone;
import me.Tiernanator.Zoning.Zone.EventCallers.Fire.OnBlockBurnInZone;
import me.Tiernanator.Zoning.Zone.EventCallers.Fire.OnFireSpreadInZone;
import me.Tiernanator.Zoning.Zone.EventCallers.Player.OnPlayerEnterLeaveZone;
import me.Tiernanator.Zoning.Zone.EventCallers.Player.OnPlayerInteractInZone;
import me.Tiernanator.Zoning.Zone.Events.Blocks.PlayerBreakBlockInZone;
import me.Tiernanator.Zoning.Zone.Events.Blocks.PlayerPlaceBlockInZone;
import me.Tiernanator.Zoning.Zone.Events.Explosions.ExplosionInZone;
import me.Tiernanator.Zoning.Zone.Events.Fire.FireInZone;
import me.Tiernanator.Zoning.Zone.Events.Fire.FireSpreadInZone;
import me.Tiernanator.Zoning.Zone.Events.Fire.OnPlayerFlintAndSteekIgniteInZone;
import me.Tiernanator.Zoning.Zone.Events.Player.OnPlayerEnterZone;
import me.Tiernanator.Zoning.Zone.Events.Player.OnPlayerLeaveZone;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

		initialiseSQL();
		registerCommands();
		registerEvents();
		registerConstants();
		registerTasks();

	}

	@Override
	public void onDisable() {

	}

	private void registerCommands() {
		getCommand("conquer").setExecutor(new PlayerConquer(this));
		getCommand("renameZone").setExecutor(new NameMyZone(this));
		getCommand("addZone").setExecutor(new AddZone(this));
//		getCommand("highlightZone").setExecutor(new HighlightMyZone(this));
	}

	private void registerEvents() {

		PluginManager pm = getServer().getPluginManager();

		//EventCallers
		pm.registerEvents(new OnPlayerBreakBlockInZone(this), this);
		pm.registerEvents(new OnPlayerPlaceBlockInZone(this), this);
		pm.registerEvents(new OnEntityExplodeInZone(this), this);
		pm.registerEvents(new OnBlockBurnInZone(this), this);
		pm.registerEvents(new OnFireSpreadInZone(this), this);
		pm.registerEvents(new OnPlayerEnterLeaveZone(this), this);
		pm.registerEvents(new OnPlayerInteractInZone(this), this);
		
		//Event Handlers
		pm.registerEvents(new PlayerBreakBlockInZone(this), this);
		pm.registerEvents(new PlayerPlaceBlockInZone(this), this);
		pm.registerEvents(new ExplosionInZone(this), this);
		pm.registerEvents(new FireInZone(this), this);
		pm.registerEvents(new FireSpreadInZone(this), this);
		pm.registerEvents(new OnPlayerFlintAndSteekIgniteInZone(this), this);
		pm.registerEvents(new OnPlayerEnterZone(this), this);
		pm.registerEvents(new OnPlayerLeaveZone(this), this);

		pm.registerEvents(new BlockPlaceRegulator(this), this);
	}

	private void registerConstants() {

		Zone.setPlugin(this);
		ZoneAccessor.setPlugin(this);

	}
	
	private void registerTasks() {
		//in runTaskTimer() first number is how long you wait the first time to start it
		// the second is how long between iterations
		PreiodicZoneRefresher preiodicZoneRefresher = new PreiodicZoneRefresher(this);
		preiodicZoneRefresher.runTaskTimerAsynchronously(this, 0, 36000);
	}

	private static MySQL mySQL;

	private void initialiseSQL() {

		mySQL = new MySQL(SQLServer.HOSTNAME, SQLServer.PORT, SQLServer.DATABASE,
				SQLServer.USERNAME, SQLServer.PASSWORD);

		Connection connection = null;
		try {
			connection = mySQL.openConnection();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "USE " + SQLServer.DATABASE.getInfo() + ";";

		statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "CREATE TABLE IF NOT EXISTS Zone ( "
				+ "Name varchar(255) NOT NULL,"
				+ "DisplayName varchar(255),"
				+ "X int,"
				+ "Y int,"
				+ "Z int,"
				+ "World varchar(36),"
				+ "Radius int,"
				+ "Owners varchar(760),"
				+ "Shared Boolean"
				+ ");";

		statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static MySQL getSQL() {
		return mySQL;
	}

}
