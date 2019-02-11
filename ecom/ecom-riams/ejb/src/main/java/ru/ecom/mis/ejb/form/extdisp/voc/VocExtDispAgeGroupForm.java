package ru.ecom.mis.ejb.form.extdisp.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAgeGroup;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocExtDispAgeGroup.class)
@Comment("Возрастные группы")
@WebTrail(comment = "Возрастные группы", nameProperties= "id"
, view="entityView-extDisp_vocAgeGroup.do")
@Parent(property="dispType", parentForm=VocExtDispForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Voc/AgeGroup")
public class VocExtDispAgeGroupForm extends IdEntityForm {
	/** Вид доп. диспансеризации */
	@Comment("Вид доп. диспансеризации")
	@Persist
	public Long getDispType() {return theDispType;}
	public void setDispType(Long aDispType) {theDispType = aDispType;}

	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Возрастная группа для отчета */
	@Comment("Возрастная группа для отчета")
	@Persist @Required
	public Long getReportGroup() {return theReportGroup;}
	public void setReportGroup(Long aReportGroup) {theReportGroup = aReportGroup;}

	/** Возрастная группа для отчета */
	private Long theReportGroup;
	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
	/** Вид доп. диспансеризации */
	private Long theDispType;
	
	/** Архивная */
	@Comment("Архивная")
	@Persist
	public Boolean getIsArchival() {return theIsArchival;}
	public void setIsArchival(Boolean aIsArchival) {theIsArchival = aIsArchival;}
	/** Архивная */
	private Boolean theIsArchival;
}
