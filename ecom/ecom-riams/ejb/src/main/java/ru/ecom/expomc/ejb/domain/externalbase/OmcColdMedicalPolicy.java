package ru.ecom.expomc.ejb.domain.externalbase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Недействующий медицинский полис ОМС
 * @author azviagin
 *
 */

@Comment("Недействующий медицинский полис ОМС")
@Entity
@AIndexes({
@AIndex (name="index", unique = false, properties = {"insuranceCode", "number", "series"})
})
@Table(schema="SQLUser")
public class OmcColdMedicalPolicy extends BaseEntity{	
	/** Код ОМС страховой компании */
	@Comment("Код ОМС страховой компании")
	public int getInsuranceCode() {return theInsuranceCode;}
	public void setInsuranceCode(int aInsuranceCode) {theInsuranceCode = aInsuranceCode;}

	
	/** Номер */
	@Comment("Номер")
	public int getNumber() {return theNumber;}
	public void setNumber(int aNumber) {theNumber = aNumber;}

	/** Серия */
	@Comment("Серия")
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}

	/** Дата начача действия */
	@Comment("Дата начача действия")
	public Date getActualBeginDate() {return theActualBeginDate;}
	public void setActualBeginDate(Date aActualBeginDate) {theActualBeginDate = aActualBeginDate;}
	
	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getActualEndDate() {return theActualEndDate;}
	public void setActualEndDate(Date aActualEndDate) {theActualEndDate = aActualEndDate;}

	/** Дата окончания действия */
	private Date theActualEndDate;
	/** Дата начача действия */
	private Date theActualBeginDate;
	/** Код ОМС страховой компании */
	private int theInsuranceCode;
	/** Номер */
	private int theNumber;
	/** Серия */
	private String theSeries;
}
