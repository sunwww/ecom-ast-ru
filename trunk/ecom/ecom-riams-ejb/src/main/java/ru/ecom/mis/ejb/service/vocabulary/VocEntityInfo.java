package ru.ecom.mis.ejb.service.vocabulary;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class VocEntityInfo  extends ru.ecom.ejb.services.vocentity.VocEntityInfo {

	public VocEntityInfo(String aClassname,String aSimpleName, String aComment, int aCount, boolean aIsSystem) {
		super(aClassname, aComment, aCount);
		theIsSystem = aIsSystem ;
		theSimpleName = aSimpleName ;
	}
	
	/** Системный справочник */
	@Comment("Системный справочник")
	public boolean getIsSystem() {return theIsSystem;}
	public void setIsSystem(boolean aIsSystem) {theIsSystem = aIsSystem;}

	/** Короткое название */
	public String getSimpleName() {return theSimpleName;}
	public void setSimpleName(String aSimpleName) {theSimpleName = aSimpleName;}

	/** Короткое название */
	private String theSimpleName;
	/** Системный справочник */
	private boolean theIsSystem;

}
