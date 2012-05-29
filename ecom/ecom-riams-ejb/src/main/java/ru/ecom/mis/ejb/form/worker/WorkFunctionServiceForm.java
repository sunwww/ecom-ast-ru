package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.WorkFunctionService;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz=WorkFunctionService.class)
@Comment("Прикрепление раб. функции к услуге")
@WebTrail(comment = "Прикрепление раб. функции к услуге"
	, nameProperties= "id"
		, view="entityParentView-mis_medService_workFunction.do"
//			,list = "entityParentList-mis_medService.do"
				)
@Parent(property="medService", parentForm= MedServiceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService/VocWorkFunction")
public class WorkFunctionServiceForm extends IdEntityForm {
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist @Required
	public Long getVocWorkFunction() {
		return theVocWorkFunction;
	}

	public void setVocWorkFunction(Long aVocWorkFunction) {
		theVocWorkFunction = aVocWorkFunction;
	}

	/** Рабочая функция */
	private Long theVocWorkFunction;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@Persist @Required
	public Long getMedService() {
		return theMedService;
	}

	public void setMedService(Long aMedService) {
		theMedService = aMedService;
	}

	/** Мед. услуга */
	private Long theMedService;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;

}
