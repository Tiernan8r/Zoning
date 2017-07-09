package me.Tiernanator.Zoning.Zone;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import me.Tiernanator.SQL.SQLServer;

public class ZoneAccessor {

	public static void setPlugin() {
	}

	public static boolean isZone(String name) {

		List<String> allZones = new ArrayList<String>();
		allZones = allZones();

		if (allZones == null) {
			return false;
		}

		if (allZones.isEmpty() || allZones.equals(null)
				|| allZones.size() <= 0) {
			return false;
		}
		if (allZones.contains(name)) {
			return true;
		} else {
			return false;
		}
	}

	public static void setZoneName(String name, String newName) {

		String statement = "UPDATE Zone SET Name = ? WHERE Name = ?;";
		Object[] values = new Object[]{newName, name};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static Location getZoneCentre(String name) {

		String query = "SELECT World, X, Y, Z FROM Zone WHERE Name = '" + name
				+ "';";

		Location centre = SQLServer.getLocation(query);

		centre.add(0.5, 0, 0.5);

		return centre;

	}

	public static void setZoneCentre(String name, Location centre) {

		int x = centre.getBlockX();
		int y = centre.getBlockY();
		int z = centre.getBlockZ();

		String statement = "UPDATE Zone SET X = ? AND Y = ? AND Z = ? WHERE Name = ?;";
		Object[] values = new Object[]{x, y, z, name};
		SQLServer.executePreparedStatement(statement, values);

		setZoneWorld(name, centre.getWorld());

	}

	public static int getZoneRadius(String name) {

		String query = "SELECT Radius FROM Zone WHERE Name = '" + name + "';";

		return SQLServer.getInt(query, "Radius");
	}

	public static void setZoneRadius(String name, int radius) {

		String statement = "UPDATE Zone SET Radius = ? WHERE Name = ?;";
		Object[] values = new Object[]{radius, name};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static World getZoneWorld(String name) {

		String query = "SELECT World FROM Zone WHERE Name = '" + name + "';";
		String worldName = SQLServer.getString(query, "World");
		return Bukkit.getWorld(worldName);

	}

	public static void setZoneWorld(String name, World world) {

		String statement = "UPDATE Zone SET World = ? WHERE Name = ?;";
		Object[] values = new Object[]{world.getName(), name};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static List<String> allZones() {

		String query = "SELECT Name FROM Zone;";

		List<String> allZones = new ArrayList<String>();
		List<Object> results = SQLServer.getList(query, "Name");
		if (results == null) {
			return null;
		}
		for (Object result : results) {
			allZones.add((String) result);
		}

		return allZones;
	}

	public static void addZone(Zone zone) {
		addZone(zone.getName(), zone.getDisplayName(), zone.getCentre(),
				zone.getRadius(), zone.getOwnerUUIDs(), zone.isShared());
	}

	public static void addZone(String name, String displayName, Location centre,
			int radius, List<String> ownerUUIDs, boolean shared) {

		if (isZone(name)) {
			removeZone(name);
		}
		int x = centre.getBlockX();
		int y = centre.getBlockY();
		int z = centre.getBlockZ();
		String world = centre.getWorld().getName();

		String owners = "";
		for (String owner : ownerUUIDs) {
			if (!owners.equals("")) {
				owners += ", ";
			}
			owners += owner;
		}

		String statement = "INSERT INTO Zone (Name, X, Y, Z, World, Radius, DisplayName, Owners, Shared) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Object[] values = new Object[]{name, x, y, z, world, radius,
				displayName, owners, shared};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static void removeZone(String name) {

		String query = "DELETE FROM Zone WHERE Name = '" + name + "';";
		SQLServer.executeQuery(query);

	}

	public static List<String> getZoneOwners(String name) {

		String query = "SELECT Owners FROM Zone WHERE Name = '" + name + "';";

		List<Object> results = SQLServer.getList(query, "Owners");
		if (results == null) {
			return null;
		}

		List<String> owners = new ArrayList<String>();
		for (Object result : results) {
			owners.add((String) result);
		}

		return owners;
	}

	public static void setZoneOwner(String name, List<String> ownersUUID) {

		String owners = "";
		for (String member : ownersUUID) {
			if (!owners.equals("")) {
				owners += ",";
			}
			owners += member;
		}

		String statement = "UPDATE Zone SET Owners = ? WHERE Name = ?;";
		Object[] values = new Object[]{owners, name};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static boolean isShared(String name) {

		String query = "SELECT Shared FROM Zone WHERE Name = '" + name + "';";
		return SQLServer.getBool(query, "Shared");

	}

	public static void setShared(String name, boolean shared) {

		String statement = "UPDATE Zone SET Shared = ? WHERE Name = ?;";
		Object[] values = new Object[]{shared, name};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static String getZoneDisplayName(String name) {

		String query = "SELECT DisplayName FROM Zone WHERE Name = '" + name
				+ "';";
		return SQLServer.getString(query, "DisplayName");

	}

	public static void setZoneDisplayName(String name, String displayName) {

		String statement = "UPDATE Zone SET DisplayName = ? WHERE Name = ?;";
		Object[] values = new Object[]{displayName, name};
		SQLServer.executePreparedStatement(statement, values);

	}
}
