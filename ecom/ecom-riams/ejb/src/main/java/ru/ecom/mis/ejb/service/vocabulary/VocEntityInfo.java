package ru.ecom.mis.ejb.service.vocabulary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocEntityInfo  extends ru.ecom.ejb.services.vocentity.VocEntityInfo {

	public VocEntityInfo(String aClassname,String aSimpleName, String aComment, int aCount, boolean aIsSystem) {
		super(aClassname, aComment, aCount);
		isSystem = aIsSystem ;
		simpleName = aSimpleName ;
	}
	
	/** Короткое название */
	private String simpleName;
	/** Системный справочник */
	private boolean isSystem;

}
