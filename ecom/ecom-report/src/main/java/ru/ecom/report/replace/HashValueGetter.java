package ru.ecom.report.replace;

import java.util.HashMap;

public class HashValueGetter implements IValueGetter {

	public Object getValue(String aExpression) throws SetValueException {
		return theHash.get(aExpression) ;
	}

	public void set(String aKey, Object aValue) throws SetValueException {
		theHash.put(aKey, aValue) ;
	}
	
	private final HashMap<String, Object> theHash = new HashMap<>() ;

}
