package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= VocWorkFunction.class)
@Comment("Рабочая функция")
@WebTrail(comment = "Рабочая функция", nameProperties= "name", view="entityView-voc_workFunction.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocWorkFunction")
/*
@ASaveInterceptors(
        @AEntityFormInterceptor(VocWorkFunctionSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(VocWorkFunctionViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(VocWorkFunctionSaveInterceptor.class)
	
})*/
public class VocWorkFunctionForm extends IdEntityForm {
	/** Должности */
	@Comment("Должности")
	@Persist @Required
	public Long getVocPost() {return theVocPost;}
	public void setVocPost(Long aVocPost) {theVocPost = aVocPost;}
	
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	
	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
	/** Должности */
	private Long theVocPost;
	
	/** Короткое название */
	@Comment("Короткое название")
	@Persist
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Короткое название */
	private String theShortName;
	/** Не заполняется диагноз */
	@Comment("Не заполняется диагноз")
	@Persist
	public Boolean getIsNoDiagnosis() {return theIsNoDiagnosis;}
	public void setIsNoDiagnosis(Boolean aIsNoDiagnosis) {theIsNoDiagnosis = aIsNoDiagnosis;}

	/** Не заполняется диагноз */
	private Boolean theIsNoDiagnosis;
	/** Функциональная диагностика */
	@Comment("Функциональная диагностика")
	@Persist
	public Boolean getIsFuncDiag() {return theIsFuncDiag;}
	public void setIsFuncDiag(Boolean aIsFuncDiag) {theIsFuncDiag = aIsFuncDiag;}

	/** Лаборатория */
	@Comment("Лаборатория")
	@Persist
	public Boolean getIsLab() {return theIsLab;}
	public void setIsLab(Boolean aIsLab) {theIsLab = aIsLab;}

	/** Лучевая диагностика */
	@Comment("Лучевая диагностика")
	@Persist
	public Boolean getIsRadiationDiagnosis() {return theIsRadiationDiagnosis;}
	public void setIsRadiationDiagnosis(Boolean aIsRadiationDiagnosis) {theIsRadiationDiagnosis = aIsRadiationDiagnosis;}

	/** Лучевая диагностика */
	private Boolean theIsRadiationDiagnosis;
	/** Лаборатория */
	private Boolean theIsLab;
	/** Функциональная диагностика */
	private Boolean theIsFuncDiag;
	
	/** Не включать в 039 форму */
	@Comment("Не включать в 039 форму")
	@Persist
	public Boolean getIsNo039() {return theIsNo039;}
	public void setIsNo039(Boolean aIsNo039) {theIsNo039 = aIsNo039;}

	/** Не включать в 039 форму */
	private Boolean theIsNo039;

	/** Профиль медицинской помощи */
	@Comment("Профиль медицинской помощи")
	@Persist
	public Long getMedHelpProfile() {return theMedHelpProfile;}
	public void setMedHelpProfile(Long aMedHelpProfile) {theMedHelpProfile = aMedHelpProfile;}
	/** Профиль медицинской помощи */
	private Long theMedHelpProfile ;
}
