package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник видов дополнительной диспансеризации
	 */
	@Comment("Справочник видов дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
public class VocExtDisp extends VocBaseEntity{
	/** Внутренний код */
	@Comment("Внутренний код")
	public String getInternalCode() {return theInternalCode;}
	public void setInternalCode(String aInternalCode) {theInternalCode = aInternalCode;}

	/** Внутренний код */
	private String theInternalCode;
	
/** Флаг медосмотра */
@Comment("Флаг медосмотра")
public Boolean getIsComission() {return theIsComission;}
public void setIsComission(Boolean aIsComission) {theIsComission = aIsComission;}
/** Флаг медосмотра */
private Boolean theIsComission;
	

	/** Тип */
	private String theType;
}
