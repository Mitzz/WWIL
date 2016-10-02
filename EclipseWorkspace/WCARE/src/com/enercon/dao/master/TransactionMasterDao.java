package com.enercon.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.model.master.RoleMasterVo;
import com.enercon.model.master.TransactionMasterVo;

public class TransactionMasterDao implements WcareConnector{
	
	private static Logger logger = Logger.getLogger(TransactionMasterDao.class);
	
	private TransactionMasterDao(){}
	
	private static class SingletonHelper{
		public final static TransactionMasterDao INSTANCE = new TransactionMasterDao();
	}
	
	public static TransactionMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}

	public List<TransactionMasterVo> get(RoleMasterVo role)
			throws SQLException {

		List<TransactionMasterVo> transactions = new ArrayList<TransactionMasterVo>();
		TransactionMasterVo transaction = null;

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String id;
		String name;
		String description;
		String url;
		String under1;
		String under2;
		String under3;
		int sequenceNo;
		String display;
		String under1ImageNormal;
		String under1ImageMouse;
		String under1ImageExpand;
		String under2ImageNormal;
		String under2ImageMouse;
		String under2ImageExpand;
		String under3ImageNormal;
		String under3ImageMouse;
		String under3ImageExpand;
		String linkNormal;
		String linkMouse;
		String linkExpand;

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "SELECT A.S_TRANSACTION_ID,A.S_TRANSACTION_NAME ,A.S_URL, A.S_UNDER_1,A.S_UNDER_2,A.S_UNDER_3, A.S_TRANSACTION_DESCRIPTION " +
			        ",S_DISPLAY,S_UNDER1_IMAGE_NORMAL,S_UNDER2_IMAGE_NORMAL,S_UNDER3_IMAGE_NORMAL,S_UNDER1_IMAGE_MOUSE,S_UNDER2_IMAGE_MOUSE," +
			        "S_UNDER3_IMAGE_MOUSE,S_UNDER1_IMAGE_EXPAND,S_UNDER2_IMAGE_EXPAND,S_UNDER3_IMAGE_EXPAND,S_LINK_NORMAL,S_LINK_MOUSE,S_LINK_EXPAND,A.N_SEQ_NO " +
			        " FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A " +
			        "WHERE B.S_ROLE_ID = ? AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID ORDER BY A.S_UNDER_1,A.N_SEQ_NO";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, role.getId());
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {

				id = rs.getString("S_TRANSACTION_ID");
                name = rs.getString("S_TRANSACTION_NAME");
                url = rs.getString("S_URL");
                under1 = rs.getString("S_UNDER_1");
                under2 = rs.getString("S_UNDER_2");
                under3 = rs.getString("S_UNDER_3");
                sequenceNo = rs.getInt("N_SEQ_NO");
                description =rs.getString("S_TRANSACTION_DESCRIPTION");
                display = rs.getString("S_DISPLAY");
                under1ImageNormal = rs.getString("S_UNDER1_IMAGE_NORMAL");
                under1ImageMouse = rs.getString("S_UNDER1_IMAGE_MOUSE");
                under1ImageExpand = rs.getString("S_UNDER1_IMAGE_EXPAND");
                under2ImageNormal = rs.getString("S_UNDER2_IMAGE_NORMAL");
                under2ImageMouse = rs.getString("S_UNDER2_IMAGE_MOUSE");
                under2ImageExpand = rs.getString("S_UNDER2_IMAGE_EXPAND");
                under3ImageNormal = rs.getString("S_UNDER3_IMAGE_NORMAL");
                under3ImageMouse = rs.getString("S_UNDER3_IMAGE_MOUSE");
                under3ImageExpand = rs.getString("S_UNDER3_IMAGE_EXPAND");
                linkNormal = rs.getString("S_LINK_NORMAL");
                linkMouse = rs.getString("S_LINK_MOUSE");
                linkExpand = rs.getString("S_LINK_EXPAND");
                
                transaction = new TransactionMasterVo.TransactionMasterVoBuilder().id(id).name(name).description(description).url(url).under1(under1)
                		.under2(under2).under3(under3).sequenceNo(sequenceNo).description(description).display(display).under1ImageNormal(under1ImageNormal).under1ImageMouse(under1ImageMouse)
                		.under1ImageExpand(under1ImageExpand).under2ImageNormal(under2ImageNormal).under2ImageMouse(under2ImageMouse)
                		.under2ImageExpand(under2ImageExpand).under3ImageNormal(under3ImageNormal).under3ImageMouse(under3ImageMouse)
                		.under3ImageExpand(under3ImageExpand).linkNormal(linkNormal).linkMouse(linkMouse).linkExpand(linkExpand).build();
                
                transactions.add(transaction);
			}

			
			logger.debug("Transaction Size: " + transactions.size());
			return transactions;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}

	}
}
