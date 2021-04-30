package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.form.voc.federal.VocE2FondV021Form;
import ru.ecom.mis.ejb.domain.medcase.MedServiceComplexLink;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Связь комплексной услуги и услуги, входящей в эту комплексную
 * @author Milamesher
 */
@EntityForm
@EntityFormPersistance(clazz=MedServiceComplexLink.class)
@Comment("Общие услуги для специальности")
@WebTrail(comment = "Общие услуги для специальности"
	, nameProperties= "innerMedService"
		, view="entityParentView-e2_vocFondV021.do"
				)
@Parent(property="speciality", parentForm= VocE2FondV021Form.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService")
@Setter
public class MedServiceComplexLinkBySpecialityForm extends MedServiceComplexLinkForm  {

	/** Комплексная мед. услуга */
	@Comment("Комплексная мед. услуга")
	@Persist
	public Long getComplexMedService() {return complexMedService;}
	private Long complexMedService;

	/** Мед. услуга в программе комплексной*/
	@Comment("Мед. услуга в программе комплексной")
	@Persist
	@Required
	public Long getInnerMedService() {return innerMedService;}
	private Long innerMedService;

	/** Количество */
	@Comment("Количество")
	@Persist
	@Required
	public Integer getCountInnerMedService() {return countInnerMedService;}
	private Integer countInnerMedService;

	/** Специальность врача */
	@Comment("Специальность врача")
	@Persist @Required
	public Long getSpeciality() {return speciality;}
	private Long speciality ;

	/** Выбрана по умолчанию */
	@Comment("Выбрана по умолчанию")
	@Persist
	public Boolean getIsDefault() {return isDefault;}
	private Boolean isDefault ;

	/** Вес */
	@Comment("Вес")
	@Persist
	public Integer getWeight() {return weight;}
	private Integer weight ;

}
