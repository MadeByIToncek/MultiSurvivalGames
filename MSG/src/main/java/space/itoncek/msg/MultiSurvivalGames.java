package space.itoncek.msg;

import com.github.puregero.multilib.MultiLib;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import space.itoncek.msg.database.DBMGR;
import space.itoncek.msg.exceptions.ConfigEntryNotFoundException;
import space.itoncek.msg.protocol.GCRequests;

import java.sql.SQLException;

public final class MultiSurvivalGames extends JavaPlugin {
    public static FileConfiguration cfg;
    public static DBMGR dbmgr;
	public static boolean isMaster;
	public static MultiSurvivalGames pl;

	@Override
    public void onEnable() {
        // Plugin startup logic

		saveDefaultConfig();
        cfg = getConfig();
		pl = this;
		try {
			dbmgr = new DBMGR(cfg.getString("db.host"),
					cfg.getInt("db.port"),
					cfg.getString("db.database"),
					cfg.getString("db.user"),
					cfg.getString("db.password"));
			isMaster = dbmgr.getConfigEntry("master").equals(MultiLib.getLocalServerName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ConfigEntryNotFoundException e) {
			try {
				dbmgr.setConfigEntry("master",MultiLib.getLocalServerName());
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			} finally {
				isMaster = true;
			}
		}

		registerMultipaperChannels();
	}

	public void registerMultipaperChannels() {
		MultiLib.onString(this,"msg:gc", (msg) -> {
			switch (GCRequests.valueOf(msg)) {
				case MASTER_SYNC -> {
					try {
						isMaster = dbmgr.getConfigEntry("master").equals(MultiLib.getLocalServerName());
					} catch (SQLException e) {
						throw new RuntimeException(e);
					} catch (ConfigEntryNotFoundException e) {
						try {
							dbmgr.setConfigEntry("master",MultiLib.getLocalServerName());
						} catch (SQLException ex) {
							throw new RuntimeException(ex);
						} finally {
							isMaster = true;
						}
					}

				}
			}
		});
	}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
