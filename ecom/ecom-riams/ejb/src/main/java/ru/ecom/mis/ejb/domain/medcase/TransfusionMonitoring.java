package ru.ecom.mis.ejb.domain.medcase;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.voc.VocUrineColor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Наблюделие за состоянием реципиента")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="transfusion")
    })

@Getter
@Setter
public class TransfusionMonitoring extends BaseEntity{
	/** Переливание */
	@Comment("Переливание")
	@OneToOne
	public Transfusion getTransfusion() {return transfusion;}
	/** Моча */
	@Comment("Моча")
	@OneToOne
	public VocUrineColor getUrineColor() {return urineColor;}
	/** Моча */
	private VocUrineColor urineColor;
	/** Переливание */
	private Transfusion transfusion;
	/** Артериальное давление (нижнее) */
	private Integer bloodPressureLower;
	/** Артериальное давление (верхнее) */
	private Integer bloodPressureTop;
	/** Температура */
	private BigDecimal temperature;
	/** Частота пульса */
	private Integer pulseRate;
	/** Кол-во часов */
	private Integer hourAfterTransfusion;
	/** Диурез */
	private Integer diuresis;
}
