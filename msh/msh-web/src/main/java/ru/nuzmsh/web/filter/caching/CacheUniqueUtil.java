package ru.nuzmsh.web.filter.caching;

public class CacheUniqueUtil {

	private static String UNIQUE_ID = "-CA_NOTSETTED";
	
	protected static void setUniqueId(String aUniquId) {
		UNIQUE_ID = aUniquId ;
	}
	
	public static String getUniqueId() {
		return UNIQUE_ID ;
	}
}
