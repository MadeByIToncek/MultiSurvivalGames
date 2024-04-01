package space.itoncek.mptest;

import com.github.puregero.multilib.MultiLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class TPOtherCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		JSONObject req = new JSONObject();
		req.put("target",args[0]);
		req.put("x",Double.parseDouble(args[1]));
		req.put("y",Double.parseDouble(args[2]));
		req.put("z",Double.parseDouble(args[3]));

		MultiLib.notify("mptest:tp",req.toString());
		return true;
	}
}
