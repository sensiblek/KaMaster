package kakao.mft.master.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2JDBCUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(H2JDBCUtil.class);
	
	private static String jdbcURL = "jdbc:h2:./h2db/kam";
	private static String jdbcUsername = "sa";
	private static String jdbcPassword = "";
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			logger.error("H2 DB connection error", e);
		}
		return connection;
	}
}
