package ru.ecom.ejb.services.vocentity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Информация о справочнике
 */
public class VocEntityInfo implements Serializable{

	/**
	 *  
	 * @param aClassname класс
	 * @param aComment   комментарий
	 */
	public VocEntityInfo(String aClassname, String aComment, int aCount) {
		theClassname = aClassname ;
		theComment = aComment ;
		theCount = aCount ;
	}
	/**
	 * Класс
	 */
	public String getClassname() {
		return theClassname;
	}
	
	/**
	 * Комментарий
	 */
	public String getComment() {
		return theComment ;
	}
	
	public int getCount() {
		return theCount ;
	}
	
//	public VocEntityPropertyInfo[] getPropArray() {
//		VocEntityPropertyInfo[] ret = new VocEntityPropertyInfo[theProps.size()] ;
//		for(int i=0; i<theProps.size();i++) {
//			ret[i] = theProps.get(i);
//		}
//		return ret ;
//	}
	public Collection<VocEntityPropertyInfo> getProperties() {
		return theProps ;
	}
	
	private final String theClassname ;
	private final String theComment ;
	private final ArrayList<VocEntityPropertyInfo> theProps  = new ArrayList<>(5) ;
	private final int theCount ;
}
