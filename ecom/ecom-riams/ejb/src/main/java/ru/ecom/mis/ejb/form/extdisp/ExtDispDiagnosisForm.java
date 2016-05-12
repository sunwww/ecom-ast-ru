package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispDiagnosis;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExtDispDiagnosis.class)
@Comment("Диагноз дополнительной диспансеризации")
@WebTrail(comment = "Диагноз дополнительной диспансеризации", nameProperties= "id", list="entityParentList-extdisp_extDispDiagnosis.do", view="entityParentView-extdisp_extDispDiagnosis.do")
@Parent(property="visit", parentForm=ExtDispVisitForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Service")
public class ExtDispDiagnosisForm extends IdEntityForm{
	/**
	 * Визит
	 */
	@Comment("Визит")
	@Persist
	public Long getVisit() {return theVisit;}
	public void setVisit(Long aVisit) {theVisit = aVisit;}
	/**
	 * Визит
	 */
	private Long theVisit;
	/**
	 * МКБ
	 */
	@Comment("МКБ")
	@Persist
	public Long getIdc() {return theIdc;}
	public void setIdc(Long aIdc) {theIdc = aIdc;}
	/**
	 * МКБ
	 */
	private Long theIdc;
	/**
	 * Подозрение на заболевание
	 */
	@Comment("Подозрение на заболевание")
	@Persist
	public Boolean getIsSuspicion() {return theIsSuspicion;}
	public void setIsSuspicion(Boolean aIsSuspicion) {theIsSuspicion = aIsSuspicion;}
	/**
	 * Подозрение на заболевание
	 */
	private Boolean theIsSuspicion;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/**
	 * Название
	 */
	private String theName;
}
