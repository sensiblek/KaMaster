package kakao.mft.master.dbms.h2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.dbms.H2JDBCUtil;
import kakao.mft.master.dbms.h2.dto.TransRequestDto;

public class TransRequestDao {
		
	private final Logger logger = LoggerFactory.getLogger(TransRequestDao.class);
	
	private final String TABLE_NAME = "trans_request";
	
	private final String INSERT = "INSERT INTO " + TABLE_NAME + "\r\n"
			+ "(origin, target, file_name) VALUES\rn"
			+ "(?, ?, ?);";
	private final String SELECT = "SELECT id, origin, target, file_name, file_hash\r\n"
			+ "FROM " + TABLE_NAME + " WHERE id = ?";
	private final String UPDATE_FILE_HASH = "UPDATE " + TABLE_NAME + " SET\r\n"
			+ "file_hash = ?\r\n"
			+ "WHERE id = ?";
	
	public void insert(TransRequestDto dto) {
		try (Connection con = H2JDBCUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT)) {
			pstmt.setString(1, dto.getOrigin());
			pstmt.setString(2, dto.getTarget());
			pstmt.setString(3, dto.getFileName());
			pstmt.execute();
		} catch (SQLException e) {
			logger.error("Insert data error", e);
		}
	}
	
	public TransRequestDto select(int id) {
		TransRequestDto dto = null;
		try (Connection con = H2JDBCUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SELECT)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new TransRequestDto();
				dto.setId(rs.getInt(1));
				dto.setOrigin(rs.getString(2));
				dto.setTarget(rs.getString(3));
				dto.setFileName(rs.getString(4));
			}
		} catch (SQLException e) {
			logger.error("Insert data error", e);
		}
		return dto;
	}
	
	public void updateFileHash(int id, String fileHash) {
		try (Connection con = H2JDBCUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE_FILE_HASH)) {
			pstmt.setString(1, fileHash);
			pstmt.setInt(2, id);
			pstmt.execute();
		} catch (SQLException e) {
			logger.error("Insert data error", e);
		}		
	}
}
