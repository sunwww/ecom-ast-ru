package ru.ecom.mis.ejb.form.psychiatry;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.psychiatry.PublicDangerousEffect;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PublicDangerousEffect.class)
@Comment("Общественно-опасное действие")
@WebTrail(comment = "Общественно-опасное действие", nameProperties= "id",list="entityParentList-psych_publicDangerousEffect.do",listComment="список по пациенту", view="entityParentView-psych_publicDangerousEffect.do")
@Parent(property="careCard", parentForm=PsychiatricCareCardForm.class,orderBy="effectDate")
@EntityFormSecurityPrefix("/Policy/Mis/Psychiatry/CareCard/PublicDangerousEffect")
public class PublicDangerousEffectForm extends IdEntityForm {
	/**
	  * Дата действия
	  */
	 @Comment("Дата действия")
	 @Persist @DoDateString @DateString @Required
	 public String getEffectDate() {
	  return theEffectDate;
	 }
	 public void setEffectDate(String aEffectDate) {
	  theEffectDate = aEffectDate;
	 }
	 /**
	  * Дата действия
	  */
	 private String theEffectDate;
	 /**
	  * Примечания
	  */
	 @Comment("Примечания")
	 @Persist
	 public String getNotes() {
	  return theNotes;
	 }
	 public void setNotes(String aNotes) {
	  theNotes = aNotes;
	 }
	 /**
	  * Примечания
	  */
	 private String theNotes;
	 /**
	  * Статья уголовного кодекса
	  */
	 @Comment("Статья уголовного кодекса")
	 @Persist @Required
	 public Long getCriminalCodeArtical() {
	  return theCriminalCodeArtical;
	 }
	 public void setCriminalCodeArtical(Long aCriminalCodeArtical) {
	  theCriminalCodeArtical = aCriminalCodeArtical;
	 }
	 /**
	  * Статья уголовного кодекса
	  */
	 private Long theCriminalCodeArtical;
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 @Comment("Карта обратившегося за психиатрической помощью")
	 @Persist @Required
	 public Long getCareCard() {
	  return theCareCard;
	 }
	 public void setCareCard(Long aCareCard) {
	  theCareCard = aCareCard;
	 }
	 /**
	  * Карта обратившегося за психиатрической помощью
	  */
	 private Long theCareCard;
	 /** Статья уголовного кодекса (ИНФО) */
	 @Comment("Статья уголовного кодекса (ИНФО)")
	 @Persist
	 public String getCriminalCodeArticalInfo() {
	 	return theCriminalCodeArticalInfo;
	 }

	 public void setCriminalCodeArticalInfo(String aCriminalCodeArticalInfo) {
	 	theCriminalCodeArticalInfo = aCriminalCodeArticalInfo;
	 }

	 /** Статья уголовного кодекса (ИНФО) */
	 private String theCriminalCodeArticalInfo;
	 
	 /** Номер ООД */
	 @Comment("Номер ООД")
	 @Persist @Required
	 public Integer getEffectNumber() {return theEffectNumber;}
	 public void setEffectNumber(Integer aEffectNumber) {theEffectNumber = aEffectNumber;}

	 /** Номер ООД */
	 private Integer theEffectNumber;
	 
	 /** Тип общественно опасных действий */
	@Comment("Тип общественно опасных действий")
	@Persist 
	public Long getEffectType() {return theEffectType;}
	public void setEffectType(Long aEffectType) {theEffectType = aEffectType;}

	/** Тип общественно опасных действий */
	private Long theEffectType;
}
