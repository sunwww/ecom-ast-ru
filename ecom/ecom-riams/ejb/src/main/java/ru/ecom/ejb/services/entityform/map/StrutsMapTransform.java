package ru.ecom.ejb.services.entityform.map;

public class StrutsMapTransform {

	public StrutsMapTransform(String aReturnType, String aCastType, String[] aAnnotations) {
		theReturnType = aReturnType ;
		theCastType = aCastType ;
		theAnnotations = aAnnotations ;
	}

	public StrutsMapTransform(String aReturnType, String aCastType) {
		this(aReturnType, aCastType, null);
	}

	public StrutsMapTransform(String aReturnType) {
		this(aReturnType, aReturnType, null);
	}
	
	public String getReturnType() {
		return theReturnType ;
	}
	
	public String getCastType() {
		return theCastType ;
	}
	
	public String[] getAnnotaions() {
		return theAnnotations;
	}
	
	private final String theReturnType ;
	private final String theCastType ;
	private final String[] theAnnotations ; 
}
