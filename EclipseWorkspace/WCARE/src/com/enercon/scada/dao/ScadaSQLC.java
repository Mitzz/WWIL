package com.enercon.scada.dao;

public class ScadaSQLC {
    
    public ScadaSQLC() {
    }
    
    public static final String GET_LOCATIONWISE_PLANT_NO =
    		"SELECT S_PLANT_NO, S_PLANT_NO||'- ('||S_WEC_NAME||')' AS S_WEC_NAME FROM SCADADW.TBL_PLANT_MASTER WHERE S_LOCATION_NO = ? ORDER BY TO_NUMBER(S_PLANT_NO)";
    
    public static final String UPLOAD_LOCATIONWISE_SCADA_DATA = "";
}
