package space.itoncek.msg.commands;

import com.github.puregero.multilib.MultiLib;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.itoncek.msg.generic.Team;
import space.itoncek.msg.protocol.GCRequests;

import java.sql.SQLException;
import java.util.List;

import static space.itoncek.msg.MultiSurvivalGames.dbmgr;
import static space.itoncek.msg.MultiSurvivalGames.isMaster;

public class StartRoundCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if ((sender.hasPermission("msg.command.startround") || sender.isOp()) && sender instanceof Player p) {
			// CLAIM BEING THE MASTER
			if (!isMaster) {
				try {
					dbmgr.setConfigEntry("master", MultiLib.getLocalServerName());
				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					MultiLib.notify("msg:gc", GCRequests.MASTER_SYNC.name());
				}
			}

			try {
				List<Location> spawns = dbmgr.getSpawns(p.getWorld());
				List<Team> teams = dbmgr.getAllTeams();
				if(spawns.size() >= teams.size()) {
					for (int i = 0; i < teams.size(); i++) {
						Team team = teams.get(i);
						Location spawn = spawns.get(i);
						for (int j = 0; j < team.players.size(); j++) {
							Location location = spawn.clone().add(0,j*3,0);
							createBoxForSpawn(location);
							team.players.get(j).teleportAsync(location);
						}
					}
				} else {
					sender.sendMessage(Component.text("Nedostatek míst pro týmy", TextColor.color(255,0,0)));
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return true;
	}

	private void createBoxForSpawn(Location loc) {
		//FLOOR
		loc.clone().subtract(0,1,0).getBlock().setType(Material.GLASS);
		//SIDES
		loc.clone().add(1,1,0).getBlock().setType(Material.GLASS);
		loc.clone().add(-1,1,0).getBlock().setType(Material.GLASS);
		loc.clone().add(0,1,-1).getBlock().setType(Material.GLASS);
		loc.clone().add(0,1,1).getBlock().setType(Material.GLASS);
	}
}
