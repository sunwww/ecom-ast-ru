package ru.ecom.expomc.ejb.domain.omcvoc;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Уровень отделений ЛПУ в ОМС
 * @author azviagin
 *
 */

@Comment("Уровень отделений ЛПУ в ОМС")
@Entity
@Table(schema="SQLUser")
public class OmcLpuDepartmentLevel extends BaseEntity{
	
	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getDateTo() {
		return theDateTo;
	}

	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}

	/** Дата окончания действия */
	private Date theDateTo;
	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getDateFrom() {
		return theDateFrom;
	}

	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}

	/** Дата начала действия */
	private Date theDateFrom;
	
	/** Тип отделения ОМС */
	@Comment("Тип отделения ОМС")
	public String getOmcDepartmentType() {
		return theOmcDepartmentType;
	}

	public void setOmcDepartmentType(String aOmcDepartmentType) {
		theOmcDepartmentType = aOmcDepartmentType;
	}

	/** Тип отделения ОМС */
	private String theOmcDepartmentType;
	
	/** Код ЛПУ в ОМС */
	@Comment("Код ЛПУ в ОМС")
	public Integer getOmcLpu() {
		return theOmcLpu;
	}

	public void setOmcLpu(Integer aOmcLpu) {
		theOmcLpu = aOmcLpu;
	}

	/** Код ЛПУ в ОМС */
	private Integer theOmcLpu;
	
	/** Уровень ОМС */
	@Comment("Уровень ОМС")
	public Integer getOmcLevel() {
		return theOmcLevel;
	}

	public void setOmcLevel(Integer aOmcLevel) {
		theOmcLevel = aOmcLevel;
	}

	/** Уровень ОМС */
	private Integer theOmcLevel;
	
	/** Название отделения */
	@Comment("Название отделения")
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Название отделения */
	private String theName;

}
