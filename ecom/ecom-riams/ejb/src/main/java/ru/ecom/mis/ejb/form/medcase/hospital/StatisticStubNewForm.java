package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.StatisticStubNew;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Новый номер стат.карты
 * @author stkacheva
 */
@EntityForm
@EntityFormPersistance(clazz=StatisticStubNew.class)
@Comment("Новый номер стат.карты")
@WebTrail(comment = "Новый номер стат.карты"
	, nameProperties= "info"
		, view="entityView-stac_statisticStub.do"
			,list = "entityParentList-stac_statisticStub.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Config/Hospital/StatisticStubNew")
public class StatisticStubNewForm extends IdEntityForm{
	/** Год */
	@Comment("Год")
	@Persist @Required
	public Long getYear() {return theYear;}
	public void setYear(Long aYear) {theYear = aYear;}

	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return theInfo;}
	public void setInfo(String aInfo) {theInfo = aInfo;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist 
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** Лечебное учреждение */
	private Long theLpu;
	/** Информация */
	private String theInfo;
	/** Код */
	private String theCode;
	/** Год */
	private Long theYear;
	
	
	/** Приемник */
	@Comment("Приемник")
	@Persist
	public Long getPigeonHole() {return thePigeonHole;}
	public void setPigeonHole(Long aPigeonHole) {thePigeonHole = aPigeonHole;}

	/** Экстренно */
	@Comment("Экстренно")
	@Persist
	public Boolean getIsEmergency() {return theIsEmergency;}
	public void setIsEmergency(Boolean aIsEmergency) {theIsEmergency = aIsEmergency;}

	/** Планово */
	@Comment("Планово")
	@Persist
	public Boolean getIsPlan() {return theIsPlan;}
	public void setIsPlan(Boolean aIsPlan) {theIsPlan = aIsPlan;}

	/** Планово */
	private Boolean theIsPlan;
	/** Экстренно */
	private Boolean theIsEmergency;
	/** Приемник */
	private Long thePigeonHole;

}
