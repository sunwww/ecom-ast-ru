package ru.ecom.mis.ejb.domain.prescription.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * справочник типов назначений
 * @author oegorova
 *
 */

@Comment ("Справочник типов назначений")
@Entity
@Table(schema="SQLUser")
public class VocPrescriptType extends VocBaseEntity {
	/** Сокращенное название */
	@Comment("Сокращенное название")
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}
	/** Сокращенное название */
	private String theShortName;
	
	/** Назначения с указанным типом можно назначать только на текущий день */
	@Comment("Назначения с указанным типом можно назначать только на текущий день")
	public Boolean getIsOnlyCurrentDate() {return theIsOnlyCurrentDate;}
	public void setIsOnlyCurrentDate(Boolean aIsOnlyCurrentDate) {theIsOnlyCurrentDate = aIsOnlyCurrentDate;}
	/** Назначения с указанным типом можно назначать только на текущий день */
	private Boolean theIsOnlyCurrentDate;

}
