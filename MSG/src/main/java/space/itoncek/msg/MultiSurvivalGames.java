package space.itoncek.msg;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import space.itoncek.msg.database.DBMGR;

import java.sql.SQLException;

public final class MultiSurvivalGames extends JavaPlugin {
    public static FileConfiguration cfg;
    public static DBMGR dbmgr;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        cfg = getConfig();
		try {
			dbmgr = new DBMGR(cfg.getString("db.host"),
					cfg.getInt("db.port"),
					cfg.getString("db.database"),
					cfg.getString("db.user"),
					cfg.getString("db.password"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
