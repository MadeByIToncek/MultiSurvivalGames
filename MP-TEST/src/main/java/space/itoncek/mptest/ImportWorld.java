package space.itoncek.mptest;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import static space.itoncek.mptest.MP_TEST.pl;

public class ImportWorld implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		World world = WorldCreator.name(args[0]).environment(World.Environment.NORMAL).createWorld();
		new BukkitRunnable() {
			@Override
			public void run() {
				((Player) sender).teleportAsync(world.getHighestBlockAt(0,0).getLocation().add(0,1,0));
			}
		}.runTaskLater(pl,20L);
		return true;
	}
}
