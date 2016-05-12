package ru.ecom.mis.ejb.domain.worker;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.voc.VocFinanceSource;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Table(schema="SQLUser")
public class WorkFunctionPlan extends BaseEntity {
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Источник финансирования */
	@Comment("Источник финансирования")
	@OneToOne
	public VocFinanceSource getFinanceSource() {return theFinanceSource;}
	public void setFinanceSource(VocFinanceSource aFinanceSource) {theFinanceSource = aFinanceSource;}

	/** Часы */
	@Comment("Часы")
	public BigDecimal getHours() {return theHours;}
	public void setHours(BigDecimal aHours) {theHours = aHours;}

	/** Коэффициент использования рабочего времени */
	@Comment("Коэффициент использования рабочего времени")
	public BigDecimal getWorkCoefficient() {return theWorkCoefficient;}
	public void setWorkCoefficient(BigDecimal aWorkCoefficient) {theWorkCoefficient = aWorkCoefficient;}

	/** Дата начала */
	@Comment("Дата начала")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** Дата окончания */
	private Date theDateTo;
	/** Дата начала */
	private Date theDateFrom;
	/** Коэффициент использования рабочего времени */
	private BigDecimal theWorkCoefficient;
	/** Часы */
	private BigDecimal theHours;
	/** Источник финансирования */
	private VocFinanceSource theFinanceSource;
	/** Рабочая функция */
	private WorkFunction theWorkFunction;
}
