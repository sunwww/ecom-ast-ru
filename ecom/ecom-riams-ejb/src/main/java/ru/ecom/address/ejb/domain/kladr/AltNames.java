package ru.ecom.address.ejb.domain.kladr;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Соответствие нового и старого кодов КЛАДР
 * @author vtsybulin
 *
 */
@Entity

public class AltNames extends BaseEntity implements IImportData{

	 /** Время импорта */
    public long getTime() { return theTime ; }
    public void setTime(long aTime) { theTime = aTime ; }
    private long theTime;
    
	/** Старый код */
	@Comment("Старый код")
	@AFormatFieldSuggest("OLDCODE")
	public String getOldCode() {return theOldCode;}
	public void setOldCode(String aOldCode) {theOldCode = aOldCode;}
	/** Старый код */
	private String theOldCode;
	
	/** Новый код */
	@Comment("Новый код")
	@AFormatFieldSuggest("NEWCODE")
	public String getNewCode() {return theNewCode;}
	public void setNewCode(String aNewCode) {theNewCode = aNewCode;}
	/** Новый код */
	private String theNewCode;
	
	/** Уровень */
	@Comment("Уровень")
	@AFormatFieldSuggest("LEVEL")
	public Long getLevel() {return theLevel;}
	public void setLevel(Long aLevel) {theLevel = aLevel;}
	/** Уровень */
	private Long theLevel;
}
