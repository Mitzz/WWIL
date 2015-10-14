package com.enercon.global.utils;

public class GlobalSQLC {
    public GlobalSQLC() {
    }
    public static final String GET_ALL_ROLES = 
        "select * from TBL_ROLE order by S_ROLE_NAME";
    public static final String GET_ALL_USER = 
    	"select * from TBL_EMPLOYEE order by S_EMPLOYEE_ID";
    public static final String GET_ALL_TRANSCTION_NAMES = 
        "select * from TBL_TRANSACTION order by S_TRANSACTION_NAME";
    public static final String GET_ALL_PRODUCT_CATEGORIES_BY_AUCTION_TYPE = 
        "SELECT * FROM TBL_PRODUCT_CATEGORY WHERE S_AUCTION_TYPE = ?";
    public static final String GET_ALL_CURRENCY_TYPE = 
        "select * from TBL_CURRENCY_MASTER";
    public static final String GET_ALL_SUBCATEGORIES_BY_PRODUCT = 
        "SELECT * FROM TBL_PRODUCT_SUBCATEGORY WHERE N_PRODUCT_CATEGORY_ID = ?";
    public static final String GET_ALL_TOLERANCE_FILES = 
        "select * from TBL_TOLERANCE_FILE";
    public static final String GET_ALL_TDC_FILES = 
        "select * from TBL_TDC_FILE";
    public static final String GET_ALL_IMAGE_FILES = 
        "select * from TBL_IMAGE_FILE";
    public static final String GET_ALL_LOCATION = 
        "SELECT * FROM TBL_LOCATION ";
    public static final String INSERT_CODE_GENERATE =
    	"INSERT INTO TBL_CODE_GENERATE(S_TABLE_NAME,S_CODE,N_NO) VALUES (?,?,?)";
    public static final String UPDATE_CODE_GENERATE =
    	"UPDATE TBL_CODE_GENERATE SET N_NO = ? WHERE S_TABLE_NAME = ? AND S_CODE = ?";
    public static final String GET_CODE_GENERATE =
    	"SELECT * FROM TBL_CODE_GENERATE WHERE S_TABLE_NAME = ? AND S_CODE = ?";
}
