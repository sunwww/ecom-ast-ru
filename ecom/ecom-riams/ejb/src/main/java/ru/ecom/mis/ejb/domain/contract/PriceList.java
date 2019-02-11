package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.voc.VocPrice;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
	/**
	 * Прейскурант
	 */
	@Comment("Прейскурант")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "name" }) })
public class PriceList extends BaseEntity{
	@OneToMany(mappedBy="priceList", cascade=CascadeType.ALL)
	public List<PricePosition> getPositions() {
		return thePositions;
	}
	public void setPositions(List<PricePosition> aPositions) {
		thePositions = aPositions;
	}
	/**
	 * Позиции прейскуранта
	 */
	private List<PricePosition> thePositions;
	/**
	 * Справочник типов цен
	 */
	@Comment("Справочник типов цен")
	@OneToOne
	public VocPrice getVocPrice() {
		return theVocPrice;
	}
	public void setVocPrice(VocPrice aVocPrice) {
		theVocPrice = aVocPrice;
	}
	/**
	 * Справочник типов цен
	 */
	private VocPrice theVocPrice;
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
	
	@Transient
	public String getVocPriceInfo() {
		return theVocPrice!=null? theVocPrice.getName() : "" ;
	}
	
	/** Используется по умолчанию */
	@Comment("Используется по умолчанию")
	public Boolean getIsDefault() {return theIsDefault;}
	public void setIsDefault(Boolean aIsDefault) {theIsDefault = aIsDefault;}
	/** Используется по умолчанию */
	private Boolean theIsDefault;

}
