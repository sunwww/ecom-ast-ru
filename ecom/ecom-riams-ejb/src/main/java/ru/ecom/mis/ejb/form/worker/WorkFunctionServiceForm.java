package ru.ecom.mis.ejb.form.worker;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
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
	@Persist
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
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}

	/** Профиль коек */
	private Long theBedType;
	
	/** Тип коек */
	@Comment("Тип коек")
	@Persist
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private Long theBedSubType;
	
	/** Уровень палат */
	@Comment("Уровень палат")
	@Persist
	public Long getRoomType() {return theRoomType;}
	public void setRoomType(Long aRoomType) {theRoomType = aRoomType;}

	/** Уровень палат */
	private Long theRoomType;


}
