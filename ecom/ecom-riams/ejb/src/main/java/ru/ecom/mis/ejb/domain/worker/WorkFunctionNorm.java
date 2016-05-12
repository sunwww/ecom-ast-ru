package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocWorkMedservice;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class WorkFunctionNorm extends BaseEntity {
	/** Типовая рабочая функция */
	@Comment("Типовая рабочая функция")
	@OneToOne
	public VocWorkFunction getVocWorkFunction() {return theVocWorkFunction;}
	public void setVocWorkFunction(VocWorkFunction aVocWorkFunction) {	theVocWorkFunction = aVocWorkFunction;}

	/** Тип мед. обслуживания */
	@Comment("Тип мед. обслуживания")
	@OneToOne
	public VocWorkMedservice getWorkMedservice() {return theWorkMedservice;}
	public void setWorkMedservice(VocWorkMedservice aWorkMedservice) {	theWorkMedservice = aWorkMedservice;}

	/** Минуты */
	@Comment("Минуты")
	public Long getMinutes() {return theMinutes;}
	public void setMinutes(Long aMinutes) {theMinutes = aMinutes;}

	/** Минуты */
	private Long theMinutes;
	/** Тип мед. обслуживания */
	private VocWorkMedservice theWorkMedservice;
	/** Типовая рабочая функция */
	private VocWorkFunction theVocWorkFunction;

}
