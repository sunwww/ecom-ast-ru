package ru.ecom.expomc.ejb.domain.med;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 *  КСГ 
 */
@Entity
@Comment("КСГ")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocKsg extends VocIdCodeName {

	/** Группа КСГ */
	@Comment("Группа КСГ")
	@OneToOne
	public VocKsgGroup getGroup() {return group;}
	private VocKsgGroup group;

	/** Год КСГ */
	private Integer year;

	/** Сверхдлительный КСГ (45 дней)	*/
	private Boolean longKsg;

	/** Является операцией */
	private Boolean isOperation;

	/** Оплачивать в полном объеме */
	private Boolean isFullPayment;

	/** Коэффициент затрат */
	private Double kz;

	/** Профиль помощи */
	private String profile;

	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return bedSubType;}
	private VocBedSubType bedSubType;

	/** Не учитывать КУСмо */
	@Comment("Не учитывать КУСмо")
	public Boolean getDoNotUseCusmo() {return doNotUseCusmo;}
	private Boolean doNotUseCusmo = false;

	/** Covid-19 КСГ */
	private Boolean isCovid19;


	@Comment("Доля ЗП врача в КСГ")
	@Column(precision = 7, scale = 4)
	public BigDecimal getDoctorCost() { return doctorCost;}
	private BigDecimal doctorCost;


}
