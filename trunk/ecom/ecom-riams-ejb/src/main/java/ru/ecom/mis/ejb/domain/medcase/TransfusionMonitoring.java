package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocUrineColor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Наблюделие за состоянием реципиента")
@Entity
@Table(schema="SQLUser")
public class TransfusionMonitoring extends BaseEntity{
	/** Кол-во часов */
	@Comment("Кол-во часов")
	public Integer getHourAfterTransfusion() {return theHourAfterTransfusion;}
	public void setHourAfterTransfusion(Integer aHourAfterTransfusion) {theHourAfterTransfusion = aHourAfterTransfusion;}

	/** Частота пульса */
	@Comment("Частота пульса")
	public Integer getPulseRate() {return thePulseRate;}
	public void setPulseRate(Integer aPulseRate) {thePulseRate = aPulseRate;}

	/** Температура */
	@Comment("Температура")
	public Integer getTemperature() {return theTemperature;}
	public void setTemperature(Integer aTemperature) {theTemperature = aTemperature;}

	/** Артериальное давление (верхнее) */
	@Comment("Артериальное давление (верхнее)")
	public Integer getBloodPressureTop() {return theBloodPressureTop;}
	public void setBloodPressureTop(Integer aBloodPressureTop) {theBloodPressureTop = aBloodPressureTop;}

	/** Артериальное давление (нижнее) */
	@Comment("Артериальное давление (нижнее)")
	public Integer getBloodPressureLower() {return theBloodPressureLower;}
	public void setBloodPressureLower(Integer aBloodPressureLower) {theBloodPressureLower = aBloodPressureLower;}

	/** Переливание */
	@Comment("Переливание")
	@OneToOne
	public Transfusion getTransfusion() {return theTransfusion;}
	public void setTransfusion(Transfusion aTransfusion) {theTransfusion = aTransfusion;}

	/** Моча */
	@Comment("Моча")
	@OneToOne
	public VocUrineColor getUrineColor() {return theUrineColor;}
	public void setUrineColor(VocUrineColor aUrineColor) {theUrineColor = aUrineColor;}

	/** Моча */
	private VocUrineColor theUrineColor;
	/** Переливание */
	private Transfusion theTransfusion;
	/** Артериальное давление (нижнее) */
	private Integer theBloodPressureLower;
	/** Артериальное давление (верхнее) */
	private Integer theBloodPressureTop;
	/** Температура */
	private Integer theTemperature;
	/** Частота пульса */
	private Integer thePulseRate;
	/** Кол-во часов */
	private Integer theHourAfterTransfusion;
}
