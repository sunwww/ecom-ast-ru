package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
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
@Setter
public class StatisticStubNewForm extends IdEntityForm{
	/** Год */
	@Comment("Год")
	@Persist @Required
	public Long getYear() {return year;}

	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return code;}

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInfo() {return info;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist 
	public Long getLpu() {return lpu;}

	/** Лечебное учреждение */
	private Long lpu;
	/** Информация */
	private String info;
	/** Код */
	private String code;
	/** Год */
	private Long year;
	
	
	/** Приемник */
	@Comment("Приемник")
	@Persist
	public Long getPigeonHole() {return pigeonHole;}

	/** Экстренно */
	@Comment("Экстренно")
	@Persist
	public Boolean getIsEmergency() {return isEmergency;}

	/** Планово */
	@Comment("Планово")
	@Persist
	public Boolean getIsPlan() {return isPlan;}

	/** Планово */
	private Boolean isPlan;
	/** Экстренно */
	private Boolean isEmergency;
	/** Приемник */
	private Long pigeonHole;

}
