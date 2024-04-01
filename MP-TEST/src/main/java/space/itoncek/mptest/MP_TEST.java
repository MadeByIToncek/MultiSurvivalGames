package space.itoncek.mptest;

import com.github.puregero.multilib.MultiLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

public final class MP_TEST extends JavaPlugin {

	@Override
	public void onEnable() {
		// Plugin startup logic

		getCommand("tptest").setExecutor(new TPOtherCommand());

		System.out.println("MultiLib.isMultiPaper() = " + MultiLib.isMultiPaper());
		System.out.println("MultiLib.getLocalServerName() = " + MultiLib.getLocalServerName());

		MultiLib.onString(this, "mptest:tp", (data, reply) -> {
			getLogger().info("Received " + data);
			JSONObject o = new JSONObject(data);

			if(o.getString("target").equals(MultiLib.getLocalServerName())) {
				for (Player p : MultiLib.getAllOnlinePlayers()) {
					p.teleport(new Location(p.getWorld(),
							o.getDouble("x"),
							o.getDouble("y"),
							o.getDouble("z")
					));
				}
			}
		});
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
