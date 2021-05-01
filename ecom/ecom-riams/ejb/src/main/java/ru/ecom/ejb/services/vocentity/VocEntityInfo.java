package ru.ecom.ejb.services.vocentity;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Информация о справочнике
 */
@Getter
public class VocEntityInfo implements Serializable{

	/**
	 *  
	 * @param aClassname класс
	 * @param aComment   комментарий
	 */
	public VocEntityInfo(String aClassname, String aComment, int aCount) {
		classname = aClassname ;
		comment = aComment ;
		count = aCount ;
	}

	private final String classname ;
	private final String comment ;
	private final ArrayList<VocEntityPropertyInfo> properties  = new ArrayList<>(5) ;
	private final int count ;
}
