package space.itoncek.msg.database;

import org.jetbrains.annotations.*;
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
		ResultSet rs = conn.createStatement().executeQuery("FROM teams SELECT * WHERE server = %d".formatted(getServerId(serverId)));
		List<Team> out = new ArrayList<>();
		while (rs.next()) {
			out.add(parseTeam(rs.getInt("id")));
		}
		rs.close();
		return out;
	}

	private int getServerId(String serverId) throws SQLException, ServerNotFoundException {
		ResultSet rs = conn.createStatement().executeQuery("FROM servers SELECT * WHERE name = '%s'".formatted(serverId));
		if(rs.next()) {
			return rs.getInt("id");
		} else {
			throw new ServerNotFoundException("Unable to find server with name %s".formatted(serverId));
		}
	}

	private Team parseTeam(int teamId) throws SQLException {
		ResultSet rs = conn.createStatement().executeQuery("FROM players SELECT * WHERE team = '%s'".formatted(teamId));
		List<UUID> uuids = new ArrayList<>();
		while (rs.next()) {
			uuids.add(UUID.fromString(rs.getString("uuid")));
		}
		return new Team(teamId, uuids);
	}

	public int getPlayerPoints(UUID uuid) throws SQLException, PlayerNotFoundException {
		ResultSet rs = conn.createStatement().executeQuery("FROM players SELECT * WHERE uuid = '%s'".formatted(uuid.toString()));
		if(rs.next()) {
			return rs.getInt("points");
		} else {
			throw new PlayerNotFoundException("Unable to find player with uuid " + uuid);
		}
	}

	public void addPlayerPoints(UUID uuid, int points) throws PlayerNotFoundException, SQLException {
		ResultSet rs = conn.createStatement().executeQuery("FROM players SELECT * WHERE uuid = '%s'".formatted(uuid.toString()));
		if(rs.next()) {
			conn.createStatement().executeUpdate("UPDATE players SET points = %d WHERE uuid = '%s';".formatted(rs.getInt("points") + points,uuid.toString()));
		} else {
			throw new PlayerNotFoundException("Unable to find player with uuid " + uuid);
		}
	}
}
