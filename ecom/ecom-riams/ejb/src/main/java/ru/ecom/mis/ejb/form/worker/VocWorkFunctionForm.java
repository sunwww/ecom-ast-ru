package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
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
@Setter
public class VocWorkFunctionForm extends IdEntityForm {
	/** Должности */
	@Comment("Должности")
	@Persist @Required
	public Long getVocPost() {return vocPost;}
	private Long vocPost;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return name;}
	private String name;

	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return code;}
	private String code;

	/** Короткое название */
	@Comment("Короткое название")
	@Persist
	public String getShortName() {return shortName;}
	private String shortName;

	/** Не заполняется диагноз */
	@Comment("Не заполняется диагноз")
	@Persist
	public Boolean getIsNoDiagnosis() {return isNoDiagnosis;}
	private Boolean isNoDiagnosis;

	/** Функциональная диагностика */
	@Comment("Функциональная диагностика")
	@Persist
	public Boolean getIsFuncDiag() {return isFuncDiag;}
	private Boolean isFuncDiag;

	/** Лаборатория */
	@Comment("Лаборатория")
	@Persist
	public Boolean getIsLab() {return isLab;}
	private Boolean isLab;

	/** Лучевая диагностика */
	@Comment("Лучевая диагностика")
	@Persist
	public Boolean getIsRadiationDiagnosis() {return isRadiationDiagnosis;}
	private Boolean isRadiationDiagnosis;


	/** Не включать в 039 форму */
	@Comment("Не включать в 039 форму")
	@Persist
	public Boolean getIsNo039() {return isNo039;}
	private Boolean isNo039;

	/** Профиль медицинской помощи */
	@Comment("Профиль медицинской помощи")
	@Persist
	public Long getMedHelpProfile() {return medHelpProfile;}
	private Long medHelpProfile ;

	/** Специальность по справочнику V021 */
	@Comment("Специальность по справочнику V021")
	@Persist
	public Long getFondSpeciality() {return fondSpeciality;}
	private Long fondSpeciality ;

	/** Не подавать по ОМС */
	@Comment("Не подавать по ОМС")
	@Persist
	public Boolean getIsNoOmc() {return isNoOmc;}
	private Boolean isNoOmc ;

	/** Создавать заголовок в дневнике */
	@Comment("Создавать заголовок в дневнике")
	@Persist
	public Boolean getIsDiaryTitle() {return isDiaryTitle;}
	private Boolean isDiaryTitle ;

	/** Можно назначать в инфекционном? */
	@Comment("Можно назначать в инфекционном?")
	@Persist
	public Boolean getIsSuitForCovid() {return isSuitForCovid;}
	/** Можно назначать в инфекционном?? */
	private Boolean isSuitForCovid;
}