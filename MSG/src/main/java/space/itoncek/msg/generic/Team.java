package space.itoncek.msg.generic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.TestOnly;
import space.itoncek.msg.exceptions.PlayerNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static space.itoncek.msg.MultiSurvivalGames.dbmgr;

public class Team {
	public final List<Player> players = new ArrayList<>();
	public final int teamId;

	public Team(int teamId, List<UUID> ids) {
		this.teamId = teamId;
		for (UUID id : ids) {
			players.add(Bukkit.getPlayer(id));
		}
	}

	public int getPointsForTeam() throws SQLException, PlayerNotFoundException {
		int result = 0;
		for (Player player : players) {
			result += dbmgr.getPlayerPoints(player.getUniqueId());
		}
		return result;
	}

}
