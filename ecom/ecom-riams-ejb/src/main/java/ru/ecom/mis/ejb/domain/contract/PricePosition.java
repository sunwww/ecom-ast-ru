package ru.ecom.mis.ejb.domain.contract;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.PriceList;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Позиция прейскуранта  
	 */
	@Comment("Позиция прейскуранта")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"name"})
	,@AIndex(unique= false, properties = {"priceList"})
})
public class PricePosition extends BaseEntity{
	/**
	 * Прайс-лист
	 */
	@Comment("Прайс-лист")
	@ManyToOne
	public PriceList getPriceList() {
		return thePriceList;
	}
	public void setPriceList(PriceList aPriceList) {
		thePriceList = aPriceList;
	}
	/**
	 * Прайс-лист
	 */
	private PriceList thePriceList;
	@OneToMany(mappedBy="pricePosition", cascade=CascadeType.ALL)
	public List<PriceMedService> getMedServices() {
		return theMedServices;
	}
	public void setMedServices(List<PriceMedService> aMedServices) {
		theMedServices = aMedServices;
	}
	/**
	 * Медицинские услуги
	 */
	private List<PriceMedService> theMedServices;
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
	 * Код
	 */
	@Comment("Код")
	
	public String getCode() {
		return theCode;
	}
	public void setCode(String aCode) {
		theCode = aCode;
	}
	/**
	 * Код
	 */
	private String theCode;
	/**
	 * Цена
	 */
	@Comment("Цена")
	
	public BigDecimal getCost() {
		return theCost;
	}
	public void setCost(BigDecimal aCost) {
		theCost = aCost;
	}
	/**
	 * Цена
	 */
	private BigDecimal theCost;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	
	public Date getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private Date theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	
	public Date getDateTo() {
		return theDateTo;
	}
	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private Date theDateTo;
}
