package ru.ecom.mis.ejb.domain.medcase.voc;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Методы ВМП
 */
@Entity
@Table(schema="SQLUser")
public class VocMethodHighCare extends VocBaseEntity {
	/** Код вида ВМП */
	@Comment("Код вида ВМП")
	public String getKindHighCare() {return theKindHighCare;}
	public void setKindHighCare(String aKindHighCare) {theKindHighCare = aKindHighCare;}

	/** Список диагнозов */
	@Comment("Список диагнозов")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Список диагнозов */
	private String theDiagnosis;
	/** Код вида ВМП */
	private String theKindHighCare;

	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** Дата начала */
	@Comment("Дата начала")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	/** Дата начала */
	private Date theDateFrom;
	/** Дата окончания */
	private Date theDateTo;

	/** Модель пациента */
	@Comment("Модель пациента")
	public String getPatientModel() {return thePatientModel;}
	public void setPatientModel(String aPatientModel) {thePatientModel = aPatientModel;}
	/** Модель пациента */
	private String thePatientModel ;
}
