package space.itoncek.msg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMGR {
	private final Connection conn;
	public DBMGR(String host, int port, String database, String username, String password) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://%s:%d/%s".formatted(host,port,database), username, password);
	}
	public void migrate() {

	}
}
