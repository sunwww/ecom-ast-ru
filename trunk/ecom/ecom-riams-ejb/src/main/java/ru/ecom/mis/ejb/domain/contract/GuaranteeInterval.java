package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.ContractGuaranteeGroup;
import ru.ecom.mis.ejb.domain.contract.voc.VocServedPersonStatus;
import ru.ecom.mis.ejb.domain.contract.voc.VocServiceProgram;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Интервал гарантийных документов
	 */
	@Comment("Интервал гарантийных документов")
@Entity
@Table(schema="SQLUser")
public class GuaranteeInterval extends BaseEntity{
	/**
	 * Группа гарантийных документов
	 */
	@Comment("Группа гарантийных документов")
	@ManyToOne
	public ContractGuaranteeGroup getGuaranteeGroup() {
		return theGuaranteeGroup;
	}
	public void setGuaranteeGroup(ContractGuaranteeGroup aGuaranteeGroup) {
		theGuaranteeGroup = aGuaranteeGroup;
	}
	/**
	 * Группа гарантийных документов
	 */
	private ContractGuaranteeGroup theGuaranteeGroup;
	/**
	 * Начиная с номера
	 */
	@Comment("Начиная с номера")
	
	public String getFromNumber() {
		return theFromNumber;
	}
	public void setFromNumber(String aFromNumber) {
		theFromNumber = aFromNumber;
	}
	/**
	 * Начиная с номера
	 */
	private String theFromNumber;
	/**
	 * Заканчивая номером
	 */
	@Comment("Заканчивая номером")
	
	public String getToNumber() {
		return theToNumber;
	}
	public void setToNumber(String aToNumber) {
		theToNumber = aToNumber;
	}
	/**
	 * Заканчивая номером
	 */
	private String theToNumber;
	/**
	 * Маска выбора номеров
	 */
	@Comment("Маска выбора номеров")
	
	public String getNumberMask() {
		return theNumberMask;
	}
	public void setNumberMask(String aNumberMask) {
		theNumberMask = aNumberMask;
	}
	/**
	 * Маска выбора номеров
	 */
	private String theNumberMask;
	/**
	 * Название
	 */
	@Comment("Название")
	
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
	/**
	 * Серия
	 */
	@Comment("Серия")
	
	public String getSeries() {
		return theSeries;
	}
	public void setSeries(String aSeries) {
		theSeries = aSeries;
	}
	/**
	 * Серия
	 */
	private String theSeries;
	/**
	 * Программа обслуживания
	 */
	@Comment("Программа обслуживания")
	@OneToOne
	public VocServiceProgram getServiceProgram() {
		return theServiceProgram;
	}
	public void setServiceProgram(VocServiceProgram aServiceProgram) {
		theServiceProgram = aServiceProgram;
	}
	/**
	 * Программа обслуживания
	 */
	private VocServiceProgram theServiceProgram;
	/**
	 * Статус обслуживаемой персоны
	 */
	@Comment("Статус обслуживаемой персоны")
	@OneToOne
	public VocServedPersonStatus getServedPersonStatus() {
		return theServedPersonStatus;
	}
	public void setServedPersonStatus(VocServedPersonStatus aServedPersonStatus) {
		theServedPersonStatus = aServedPersonStatus;
	}
	/**
	 * Статус обслуживаемой персоны
	 */
	private VocServedPersonStatus theServedPersonStatus;
	
	@Transient
	public String getServedPersonStatusInfo() {
		return theServedPersonStatus!=null? theServedPersonStatus.getName():"" ;
	}
	@Transient
	public String getServiceProgramInfo() {
		return theServiceProgram!=null? theServiceProgram.getName() :"" ;
	}
}
