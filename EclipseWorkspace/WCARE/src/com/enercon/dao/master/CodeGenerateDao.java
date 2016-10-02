package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.model.master.CodeGenerateVo;

public class CodeGenerateDao{
	
	private final static Logger logger = Logger.getLogger(CodeGenerateDao.class);
	
	private final static String CODE_GENERATE_NAME_NULL = "Table Name is null";
	private final static String CODE_GENERATE_SEQUENCE_CODE_NULL = "Sequence Code is null";

	private CodeGenerateDao() {
	}

	private static class SingletonHelper {
		private static final CodeGenerateDao INSTANCE = new CodeGenerateDao();
	}

	public static CodeGenerateDao getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public boolean insertOrUpdate(CodeGenerateVo vo) {
		return false;
	}
	
	private void validate(CodeGenerateVo vo) {
		if(vo.getTableName() == null) {
			logger.error(CODE_GENERATE_NAME_NULL);
			throw new NullPointerException(CODE_GENERATE_NAME_NULL);
		}
    	if(vo.getSequenceCode() == null) {
    		logger.error(CODE_GENERATE_SEQUENCE_CODE_NULL);
    		throw new NullPointerException(CODE_GENERATE_SEQUENCE_CODE_NULL);
    	}
	}

	public CodeGenerateVo retrieve(String tableName, String sequenceCode) throws SQLException {
		validate(tableName, sequenceCode);
		CodeGenerateVo vo = null;

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;

		int sequenceNo = -1;

		try {
			connection = wcareConnector.getConnectionFromPool();

			query = 
					"SELECT * " + 
					"FROM TBL_CODE_GENERATE  " + 
					"WHERE S_TABLE_NAME = ? " + 
					"AND S_CODE = ? ";

			ps = connection.prepareStatement(query);

			ps.setString(1, tableName);
			ps.setString(2, sequenceCode);

			rs = ps.executeQuery();

			while (rs.next()) {
				sequenceNo = rs.getInt("N_NO");
				vo = new CodeGenerateVo.CodeGenerateVoBuilder(tableName)
						.sequenceCode(sequenceCode).sequenceNo(sequenceNo)
						.build();
			}

			return vo;
		} finally {
			DaoUtility.releaseResources(ps, rs, connection);
		}

		
	}
	
	private void validate(String tableName, String sequenceCode) {
		validate(new CodeGenerateVo.CodeGenerateVoBuilder(tableName).sequenceCode(sequenceCode).build());
	}

	public boolean create(CodeGenerateVo vo) throws SQLException {
		validate(vo);
		Connection connection = null;
		PreparedStatement ps = null;
		String query = null;

		try {
			connection = wcareConnector.getConnectionFromPool();

			query = 
					"insert into tbl_code_generate(S_TABLE_NAME, S_Code, N_No) " + 
					"values(?, ?, 0) " ; 

			ps = connection.prepareStatement(query);

			ps.setString(1, vo.getTableName());
			ps.setString(2, vo.getSequenceCode());
			
			int insertRowCount = ps.executeUpdate();
			return insertRowCount == 1;
			
		} finally {
			DaoUtility.releaseResources(ps, connection);
		}

	}

	public boolean update(CodeGenerateVo vo) throws SQLException {
		validate(vo);
		Connection connection = null;
		PreparedStatement ps = null;
		String query = null;

		try {
			connection = wcareConnector.getConnectionFromPool();

			query = 
					"update tbl_code_generate " + 
					"set N_No = ? " + 
					"where S_TABLE_NAME = ? " + 
					"AND S_CODE = ? " ;

			ps = connection.prepareStatement(query);

			ps.setInt(1, vo.getSequenceNo());
			ps.setString(2, vo.getTableName());
			ps.setString(3, vo.getSequenceCode());
			
			int insertRowCount = ps.executeUpdate();

			return insertRowCount == 1;
			
		} finally {
			DaoUtility.releaseResources(ps, connection);
		}
	}
}
