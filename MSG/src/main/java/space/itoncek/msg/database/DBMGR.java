package space.itoncek.msg.database;

import org.bukkit.Location;
import org.bukkit.World;
import space.itoncek.msg.exceptions.ConfigEntryNotFoundException;
import space.itoncek.msg.exceptions.PlayerNotFoundException;
import space.itoncek.msg.exceptions.ServerNotFoundException;
import space.itoncek.msg.generic.Team;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBMGR {
	private final Connection conn;
	public DBMGR(String host, int port, String database, String username, String password) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://%s:%d/%s".formatted(host,port,database), username, password);
	}
	public void migrate() {

	}
	public List<Team> getLocalTeams(String serverId) throws SQLException, ServerNotFoundException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM teams WHERE server = %d".formatted(getServerId(serverId)));
		List<Team> out = new ArrayList<>();
		while (rs.next()) {
			out.add(parseTeam(rs.getInt("id")));
		}
		rs.close();
		return out;
	}

	private int getServerId(String serverId) throws SQLException, ServerNotFoundException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM servers WHERE name = '%s'".formatted(serverId));
		if(rs.next()) {
			return rs.getInt("id");
		} else {
			throw new ServerNotFoundException("Unable to find server with name %s".formatted(serverId));
		}
	}

	private Team parseTeam(int teamId) throws SQLException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM players WHERE team = '%s'".formatted(teamId));
		List<UUID> uuids = new ArrayList<>();
		while (rs.next()) {
			uuids.add(UUID.fromString(rs.getString("uuid")));
		}
		return new Team(teamId, uuids);
	}

	public int getPlayerPoints(UUID uuid) throws SQLException, PlayerNotFoundException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM players WHERE uuid = '%s'".formatted(uuid.toString()));
		if(rs.next()) {
			return rs.getInt("points");
		} else {
			throw new PlayerNotFoundException("Unable to find player with uuid " + uuid);
		}
	}

	public void addPlayerPoints(UUID uuid, int points) throws PlayerNotFoundException, SQLException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM players WHERE uuid = '%s'".formatted(uuid.toString()));
		if(rs.next()) {
			conn.createStatement().executeUpdate("UPDATE players SET points = %d WHERE uuid = '%s';".formatted(rs.getInt("points") + points,uuid.toString()));
		} else {
			throw new PlayerNotFoundException("Unable to find player with uuid " + uuid);
		}
	}

	public String getConfigEntry(String key) throws SQLException, ConfigEntryNotFoundException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM config WHERE `key` = '%s';".formatted(key));
		if(rs.next()) {
			return rs.getString("value");
		} else {
			throw new ConfigEntryNotFoundException(key);
		}
	}

	public void setConfigEntry(String key, String value) throws SQLException {
		conn.createStatement().executeUpdate("INSERT INTO config (`key`, `value`) VALUES('%s', '%s') ON DUPLICATE KEY UPDATE `key`='%s', `value`='%s';".formatted(key,value,key,value));
	}

	public List<Team> getAllTeams() throws SQLException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM teams");
		List<Team> out = new ArrayList<>();
		while (rs.next()) {
			out.add(parseTeam(rs.getInt("id")));
		}
		rs.close();
		return out;
	}

	public List<Location> getSpawns(World world) throws SQLException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM spawns WHERE `world` = '%s';".formatted(world.getName()));
		List<Location> result = new ArrayList<>();
		while (rs.next()) {
			result.add(new Location(world,
					rs.getDouble("x"),
					rs.getDouble("y"),
					rs.getDouble("z"),
					rs.getFloat("yaw"),
					rs.getFloat("pitch")));
		}
		return result;
	}
}
