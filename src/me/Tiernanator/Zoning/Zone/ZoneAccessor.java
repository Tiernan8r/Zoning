package me.Tiernanator.Zoning.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Zoning.Main;

public class ZoneAccessor {

	private static Main plugin;
	public static void setPlugin(Main main) {
		plugin = main;
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

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "UPDATE Zone SET Name = '" + newName
						+ "' WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);

		//
		// Connection connection = Main.getSQL().getConnection();
		// Statement statement = null;
		// try {
		// statement = connection.createStatement();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.execute(query);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
	}

	public static Location getZoneCentre(String name) {

		String query = "SELECT World, X, Y, Z FROM Zone WHERE Name = '" + name
				+ "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int x = 0;
		try {
			x = resultSet.getInt("X");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int y = 0;
		try {
			y = resultSet.getInt("Y");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int z = 0;
		try {
			z = resultSet.getInt("Z");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String worldName = "";
		try {
			worldName = resultSet.getString("World");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		World world = plugin.getServer().getWorld(worldName);

		Location centre = new Location(world, x, y, z);

		centre.add(0.5, 0, 0.5);

		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return centre;

	}

	public static void setZoneCentre(String name, Location centre) {

		int x = centre.getBlockX();
		int y = centre.getBlockY();
		int z = centre.getBlockZ();

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "UPDATE Zone SET X = '" + x + "' AND " + "Y = '"
						+ y + "' AND " + "Z = '" + z + "' WHERE Name = '" + name
						+ "';";

				setZoneWorld(name, centre.getWorld());

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);

		// String query = "UPDATE Zone SET X = '" + x + "' AND "
		// + "Y = '" + y + "' AND "
		// + "Z = '" + z + "' WHERE Name = '" + name + "';";
		//
		// setZoneWorld(name, centre.getWorld());
		//
		// Connection connection = Main.getSQL().getConnection();
		// Statement statement = null;
		// try {
		// statement = connection.createStatement();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.execute(query);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

	}

	public static int getZoneRadius(String name) {

		String query = "SELECT Radius FROM Zone WHERE Name = '" + name + "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int radius = 0;
		try {
			radius = resultSet.getInt("Radius");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return radius;
	}

	public static void setZoneRadius(String name, int radius) {

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "UPDATE Zone SET Radius = '" + radius
						+ "' WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);

		// String query = "UPDATE Zone SET Radius = '" + radius + "' WHERE Name
		// = '" + name + "';";
		//
		// Connection connection = Main.getSQL().getConnection();
		// Statement statement = null;
		// try {
		// statement = connection.createStatement();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.execute(query);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
	}

	public static World getZoneWorld(String name) {

		String query = "SELECT World FROM Zone WHERE Name = '" + name + "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String worldName = "";
		try {
			worldName = resultSet.getString("World");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		World world = plugin.getServer().getWorld(worldName);

		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return world;
	}

	public static void setZoneWorld(String name, World world) {

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "UPDATE Zone SET World = '" + world.getName()
						+ "' WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);

		// String query = "UPDATE Zone SET World = '" + world.getName() + "'
		// WHERE Name = '" + name + "';";
		//
		// Connection connection = Main.getSQL().getConnection();
		// Statement statement = null;
		// try {
		// statement = connection.createStatement();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.execute(query);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

	}

	public static List<String> allZones() {

		String query = "SELECT Name FROM Zone;";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		List<String> allZones = new ArrayList<String>();
		try {
			while (resultSet.next()) {
				allZones.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allZones;
	}

	public static void addZone(Zone zone) {
		addZone(zone.getName(), zone.getDisplayName(), zone.getCentre(), zone.getRadius(), zone.getOwnerUUIDs(), zone.isShared());
	}
	
	public static void addZone(String name, String displayName, Location centre,
			int radius, List<String> ownerUUIDs, boolean shared) {

		if (isZone(name)) {
			removeZone(name);
		}

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				Connection connection = Main.getSQL().getConnection();
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = connection.prepareStatement(
							"INSERT INTO Zone (Name, X, Y, Z, World, Radius, DisplayName, Owners, Shared) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(1, name);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setInt(2, centre.getBlockX());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setInt(3, centre.getBlockY());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setInt(4, centre.getBlockZ());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(5, centre.getWorld().getName());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setInt(6, radius);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(7, displayName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				String owners = "";
				for(String owner : ownerUUIDs) {
					if(!owners.equals("")) {
						owners += ", ";
					}
					owners += owner;
				}
				
				try {
					preparedStatement.setString(8, owners);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setBoolean(9, shared);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.closeOnCompletion();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		};
		runnable.runTaskAsynchronously(plugin);

		// Connection connection = Main.getSQL().getConnection();
		// PreparedStatement preparedStatement = null;
		// try {
		// preparedStatement = connection.prepareStatement(
		// "INSERT INTO Zone (Name, X, Y, Z, World, Radius, DisplayName, Owner)
		// VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setString(1, name);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setInt(2, centre.getBlockX());
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setInt(3, centre.getBlockY());
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setInt(4, centre.getBlockZ());
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setString(5, centre.getWorld().getName());
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setInt(6, radius);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setString(7, displayName);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.setString(8, owner);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// try {
		// preparedStatement.executeUpdate();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// preparedStatement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
	}

	public static void removeZone(String name) {

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "DELETE FROM Zone WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);

		// String query = "DELETE FROM Zone WHERE Name = '" + name + "';";
		//
		// Connection connection = Main.getSQL().getConnection();
		// Statement statement = null;
		// try {
		// statement = connection.createStatement();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.execute(query);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
	}

	public static List<String> getZoneOwners(String name) {

		String query = "SELECT Owners FROM Zone WHERE Name = '" + name + "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String ownersString = "";
		try {
			ownersString = resultSet.getString("Owners");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<String> owners = new ArrayList<String>();
		String[] ownersArray = ownersString.split(",");
		for (String owner : ownersArray) {
			owners.add(owner);
		}

		return owners;
	}

	public static void setZoneOwner(String name, List<String> ownersUUID) {

		String owners = "";
		for(String member : ownersUUID) {
			if(!owners.equals("")) {
				owners += ",";
			}
			owners += member;
		}
		String owner = owners;

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "UPDATE Zone SET Owners = '" + owner
						+ "' WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);

		// String query = "UPDATE Zone SET Owner = '" + owner + "' WHERE Name =
		// '" + name + "';";
		//
		// Connection connection = Main.getSQL().getConnection();
		// Statement statement = null;
		// try {
		// statement = connection.createStatement();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.execute(query);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

	}
	
	public static boolean getIsShared(String name) {

		String query = "SELECT Shared FROM Zone WHERE Name = '" + name + "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		boolean shared = false;
		try {
			shared = resultSet.getBoolean("Shared");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shared;
	}

	public static void setZoneIsShared(String name, boolean shared) {

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "UPDATE Zone SET Shared = '" + shared
						+ "' WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);

	}
	
	public static String getZoneDisplayName(String name) {

		String query = "SELECT DisplayName FROM Zone WHERE Name = '" + name
				+ "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String displayName = "";
		try {
			displayName = resultSet.getString("DisplayName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			resultSet.close();
			statement.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return displayName;
	}

	public static void setZoneDisplayName(String name, String displayName) {

		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				String query = "UPDATE Zone SET DisplayName = '" + displayName
						+ "' WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
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
		};
		runnable.runTaskAsynchronously(plugin);
		// String query = "UPDATE Zone SET DisplayName = '" + displayName + "'
		// WHERE Name = '" + name + "';";
		//
		// Connection connection = Main.getSQL().getConnection();
		// Statement statement = null;
		// try {
		// statement = connection.createStatement();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.execute(query);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// statement.closeOnCompletion();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

	}
}
