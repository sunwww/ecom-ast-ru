package ru.ecom.mis.ejb.form.medcase.transfusion;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.TransfusionMonitoring;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= TransfusionMonitoring.class)
@Comment("Переливание трансфузионных сред")
@WebTrail(comment = "Переливание трансфузионных сред", nameProperties= "id", view="entitySubclassView-trans_transfusion.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="transfusion", parentForm= TransfusionForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion")
@Subclasses({BloodTransfusionForm.class, OtherTransfusionForm.class})
@Setter
public class TransfusionMonitoringForm extends IdEntityForm {
	/** Кол-во часов */
	@Comment("Кол-во часов")
	public Integer getHourAfterTransfusion() {return hourAfterTransfusion;}

	/** Частота пульса */
	@Comment("Частота пульса")
	public Integer getPulseRate() {return pulseRate;}

	/** Температура */
	@Comment("Температура")
	@DoIntegerString @IntegerString
	public String getTemperature() {return temperature;}

	/** Артериальное давление (верхнее) */
	@Comment("Артериальное давление (верхнее)")
	public Integer getBloodPressureTop() {return bloodPressureTop;}

	/** Артериальное давление (нижнее) */
	@Comment("Артериальное давление (нижнее)")
	public Integer getBloodPressureLower() {return bloodPressureLower;}

	/** Переливание */
	@Comment("Переливание")
	@Persist @Required
	public Long getTransfusion() {return transfusion;}

	/** Переливание */
	private Long transfusion;
	/** Артериальное давление (нижнее) */
	private Integer bloodPressureLower;
	/** Артериальное давление (верхнее) */
	private Integer bloodPressureTop;
	/** Температура */
	private String temperature;
	/** Частота пульса */
	private Integer pulseRate;
	/** Кол-во часов */
	private Integer hourAfterTransfusion;

	/** Моча */
	@Comment("Моча")
	@Persist
	public Long getUrineColor() {return urineColor;}

	/** Диурез */
	@Comment("Диурез")
	@Persist
	public Integer getDiuresis() {return diuresis;}

	/** Моча */
	private Long urineColor;
	/** Диурез */
	private Integer diuresis;
}
