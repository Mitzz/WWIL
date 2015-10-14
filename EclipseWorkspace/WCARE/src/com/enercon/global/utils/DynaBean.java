package com.enercon.global.utils;

import java.util.Hashtable;
import java.util.Map;


public class DynaBean {
    private Hashtable hashTable = new Hashtable();

    public Object getProperty(String sPropName) {
        return hashTable.get(sPropName);
    }

    public void setProperty(String sPropName, String sPropValue) {
        hashTable.put(sPropName, sPropValue);
    }

    public void setMap(Map map) {
        hashTable.putAll(map);
    }

	@Override
	public String toString() {
		return "DynaBean [hashTable=" + hashTable + "]";
	}
    
    
}
