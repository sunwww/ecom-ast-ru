package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.mis.ejb.domain.medcase.MedServiceComplexLink;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceComplexLinkSaveInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Связь комплексной услуги и услуги, входящей в эту комплексную
 * @author Milamesher
 */
@EntityForm
@EntityFormPersistance(clazz=MedServiceComplexLink.class)
@Comment("Связь с комплексной услугой")
@WebTrail(comment = "Связь с комплексной услугой"
	, nameProperties= "innerMedService"
		, view="entityParentView-mis_medServiceComplexLink.do"
				)
@Parent(property="complexMedService", parentForm= MedServiceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService")
@ACreateInterceptors(
		@AEntityFormInterceptor(MedServiceComplexLinkSaveInterceptor.class)
)
@ASaveInterceptors(
		@AEntityFormInterceptor(MedServiceComplexLinkSaveInterceptor.class)
)
@Setter
public class MedServiceComplexLinkForm extends IdEntityForm  {

	/** Комплексная мед. услуга */
	@Comment("Комплексная мед. услуга")
	@Persist
	@Required
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
	@Persist
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
