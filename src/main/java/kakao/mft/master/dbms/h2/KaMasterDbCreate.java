package kakao.mft.master.dbms.h2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.dbms.H2JDBCUtil;

public class KaMasterDbCreate {
	
	private static final Logger logger = LoggerFactory.getLogger(KaMasterDbCreate.class);
	
	/* 파일 전송 요청 정보를 관리한다. */
	/**
	 * CREATE TABLE IF NOT EXISTS trans_request (
	 *   id INT(10) NOT NULL AUTO_INCREMENT,
	 *   origin VARCHAR(100) NOT NULL,
	 *   target VARCHAR(100) NOT NULL,
	 *   file_name VARCHAR(255) NOT NULL,
	 *   file_hash VARCHAR(100),
	 *   user_name VARCHAR(40) NOT NULL,
	 *   last_modified TIMESTAMP AS CURRUNT_TIMESTAMP,
	 *   PRIMARY KEY (id)
	 * );
	 */
	private static final String createRequestTable = 
			"CREATE TABLE IF NOT EXISTS trans_request (\r\n"
			+ "id INT(10) NOT NULL AUTO_INCREMENT,\r\n"
			+ "origin VARCHAR(100) NOT NULL,\r\n"
			+ "target VARCHAR(100) NOT NULL,\r\n"
			+ "file_name VARCHAR(255) NOT NULL,\r\n"
			+ "file_hash VARCHAR(100),\r\n "
			+ "user_name VARCHAR(40),\r\n"
			+ "PRIMARY KEY (id)\r\n"
			+ ");";
	
	public static void initialize() {
		try (Connection con = H2JDBCUtil.getConnection();
				Statement stat = con.createStatement()) {
			stat.execute(createRequestTable);
		} catch (SQLException e) {
			logger.error("DDL fail", e);
		}
	}
	
	public static void main(String[] args) {
		initialize();
	}
}
