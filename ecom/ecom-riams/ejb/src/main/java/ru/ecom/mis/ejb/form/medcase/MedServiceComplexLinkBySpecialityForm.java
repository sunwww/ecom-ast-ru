package ru.ecom.mis.ejb.form.medcase;

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
public class MedServiceComplexLinkBySpecialityForm extends MedServiceComplexLinkForm  {

	/** Комплексная мед. услуга */
	@Comment("Комплексная мед. услуга")
	@Persist
	public Long getComplexMedService() {return theComplexMedService;}
	public void setComplexMedService(Long aComplexMedService) {theComplexMedService = aComplexMedService;}
	private Long theComplexMedService;

	/** Мед. услуга в программе комплексной*/
	@Comment("Мед. услуга в программе комплексной")
	@Persist
	@Required
	public Long getInnerMedService() {return theInnerMedService;}
	public void setInnerMedService(Long aInnerMedService) {theInnerMedService = aInnerMedService;}
	private Long theInnerMedService;

	/** Количество */
	@Comment("Количество")
	@Persist
	@Required
	public Integer getCountInnerMedService() {return theCountInnerMedService;}
	public void setCountInnerMedService(Integer aCountInnerMedService) {theCountInnerMedService = aCountInnerMedService;}
	private Integer theCountInnerMedService;

	/** Специальность врача */
	@Comment("Специальность врача")
	@Persist @Required
	public Long getSpeciality() {return theSpeciality;}
	public void setSpeciality(Long aSpeciality) {theSpeciality = aSpeciality;}
	private Long theSpeciality ;

	/** Выбрана по умолчанию */
	@Comment("Выбрана по умолчанию")
	@Persist
	public Boolean getIsDefault() {return theIsDefault;}
	public void setIsDefault(Boolean aIsDefault) {theIsDefault = aIsDefault;}
	private Boolean theIsDefault ;

	/** Вес */
	@Comment("Вес")
	@Persist
	public Integer getWeight() {return theWeight;}
	public void setWeight(Integer aWeight) {theWeight = aWeight;}
	private Integer theWeight ;

}
