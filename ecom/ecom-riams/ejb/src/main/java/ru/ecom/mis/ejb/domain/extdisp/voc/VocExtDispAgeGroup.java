package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник возрастных групп дополнительной диспансеризации
	 */
	@Comment("Справочник возрастных групп дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
public class VocExtDispAgeGroup extends VocBaseEntity{
	/** Вид диспансеризации */
	@Comment("Вид диспансеризации")
	@OneToOne
	public VocExtDisp getDispType() {return theDispType;}
	public void setDispType(VocExtDisp aDispType) {theDispType = aDispType;}

	/** Возрастная группа для отчета */
	@Comment("Возрастная группа для отчета")
	@OneToOne
	public VocExtDispAgeReportGroup getReportGroup() {return theReportGroup;}
	public void setReportGroup(VocExtDispAgeReportGroup aReportGroup) {theReportGroup = aReportGroup;}

	/** Код диспансеризации */
	@Comment("Код диспансеризации")
	public String getDispCode() {return theDispCode;}
	public void setDispCode(String aDispCode) {theDispCode = aDispCode;}

	/** Код диспансеризации */
	private String theDispCode;
	/** Возрастная группа для отчета */
	private VocExtDispAgeReportGroup theReportGroup;
	/** Вид диспансеризации */
	private VocExtDisp theDispType;
	
	/** Архивная */
	@Comment("Архивная")
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** Архивная */
	private Boolean theIsArchival;
}
