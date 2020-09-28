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
public class VocWorkFunctionForm extends IdEntityForm {
	/** Должности */
	@Comment("Должности")
	@Persist @Required
	public Long getVocPost() {return theVocPost;}
	public void setVocPost(Long aVocPost) {theVocPost = aVocPost;}
	private Long theVocPost;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	private String theName;

	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}
	private String theCode;

	/** Короткое название */
	@Comment("Короткое название")
	@Persist
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}
	private String theShortName;

	/** Не заполняется диагноз */
	@Comment("Не заполняется диагноз")
	@Persist
	public Boolean getIsNoDiagnosis() {return theIsNoDiagnosis;}
	public void setIsNoDiagnosis(Boolean aIsNoDiagnosis) {theIsNoDiagnosis = aIsNoDiagnosis;}
	private Boolean theIsNoDiagnosis;

	/** Функциональная диагностика */
	@Comment("Функциональная диагностика")
	@Persist
	public Boolean getIsFuncDiag() {return theIsFuncDiag;}
	public void setIsFuncDiag(Boolean aIsFuncDiag) {theIsFuncDiag = aIsFuncDiag;}
	private Boolean theIsFuncDiag;

	/** Лаборатория */
	@Comment("Лаборатория")
	@Persist
	public Boolean getIsLab() {return theIsLab;}
	public void setIsLab(Boolean aIsLab) {theIsLab = aIsLab;}
	private Boolean theIsLab;

	/** Лучевая диагностика */
	@Comment("Лучевая диагностика")
	@Persist
	public Boolean getIsRadiationDiagnosis() {return theIsRadiationDiagnosis;}
	public void setIsRadiationDiagnosis(Boolean aIsRadiationDiagnosis) {theIsRadiationDiagnosis = aIsRadiationDiagnosis;}
	private Boolean theIsRadiationDiagnosis;


	/** Не включать в 039 форму */
	@Comment("Не включать в 039 форму")
	@Persist
	public Boolean getIsNo039() {return theIsNo039;}
	public void setIsNo039(Boolean aIsNo039) {theIsNo039 = aIsNo039;}
	private Boolean theIsNo039;

	/** Профиль медицинской помощи */
	@Comment("Профиль медицинской помощи")
	@Persist
	public Long getMedHelpProfile() {return theMedHelpProfile;}
	public void setMedHelpProfile(Long aMedHelpProfile) {theMedHelpProfile = aMedHelpProfile;}
	private Long theMedHelpProfile ;

	/** Специальность по справочнику V021 */
	@Comment("Специальность по справочнику V021")
	@Persist
	public Long getFondSpeciality() {return theFondSpeciality;}
	public void setFondSpeciality(Long aFondSpeciality) {theFondSpeciality = aFondSpeciality;}
	private Long theFondSpeciality ;

	/** Не подавать по ОМС */
	@Comment("Не подавать по ОМС")
	@Persist
	public Boolean getIsNoOmc() {return theIsNoOmc;}
	public void setIsNoOmc(Boolean aIsNoOmc) {theIsNoOmc = aIsNoOmc;}
	private Boolean theIsNoOmc ;

	/** Создавать заголовок в дневнике */
	@Comment("Создавать заголовок в дневнике")
	@Persist
	public Boolean getIsDiaryTitle() {return theIsDiaryTitle;}
	public void setIsDiaryTitle(Boolean aIsDiaryTitle) {theIsDiaryTitle = aIsDiaryTitle;}
	private Boolean theIsDiaryTitle ;

	/** Можно назначать в инфекционном? */
	@Comment("Можно назначать в инфекционном?")
	@Persist
	public Boolean getIsSuitForCovid() {return theIsSuitForCovid;}
	public void setIsSuitForCovid(Boolean aIsSuitForCovid) {theIsSuitForCovid = aIsSuitForCovid;}
	/** Можно назначать в инфекционном?? */
	private Boolean theIsSuitForCovid;
}