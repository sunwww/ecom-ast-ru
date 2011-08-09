package ru.ecom.expomc.ejb.domain.med;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocIdc10Plain extends VocBaseEntity implements IImportData {
	/** Класс МКБ */
	@Comment("Класс МКБ")
	public String getIdc10Class() {
		return theIdc10Class;
	}

	/** Название класса */
	@Comment("Название класса")
	public String getClassName() {
		return theClassName;
	}

	public void setClassName(String aClassName) {
		theClassName = aClassName;
	}

	/** Блок */
	@Comment("Блок")
	public String getBlock() {
		return theBlock;
	}

	public void setBlock(String aBlock) {
		theBlock = aBlock;
	}
	
	/** Название блока */
	@Comment("Название блока")
	public String getBlockName() {
		return theBlockName;
	}

	public void setBlockName(String aBlockName) {
		theBlockName = aBlockName;
	}

	public void setIdc10Class(String aIdc10Class) {
		theIdc10Class = aIdc10Class;
	}

	  /** Время импорта */
    @Column(name="voc_time")
    public long getTime() { return theTime ; }
    public void setTime(long aTime) { theTime = aTime ; }
    
    /** Время импорта */
    private long theTime ;
	/** Название блока */
	private String theBlockName;
	/** Блок */
	private String theBlock;
	/** Название класса */
	private String theClassName;

	/** Класс МКБ */
	private String theIdc10Class;
}
