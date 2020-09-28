package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.voc.VocPositionType;
import ru.ecom.mis.ejb.domain.contract.voc.VocVat;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
	 * Позиция прейскуранта  
	 */
	@Comment("Позиция прейскуранта")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"name"})
	,@AIndex(properties = {"priceList"})
	,@AIndex(properties = {"parent"})
})
public class PricePosition extends BaseEntity{
	/**
	 * Прайс-лист
	 */
	@Comment("Прайс-лист")
	@ManyToOne
	public PriceList getPriceList() {return thePriceList;}
	public void setPriceList(PriceList aPriceList) {thePriceList = aPriceList;}
	/**
	 * Прайс-лист
	 */
	private PriceList thePriceList;
	@OneToMany(mappedBy="pricePosition", cascade=CascadeType.ALL)
	public List<PriceMedService> getMedServices() {return theMedServices;}
	public void setMedServices(List<PriceMedService> aMedServices) {theMedServices = aMedServices;}
	/**
	 * Медицинские услуги
	 */
	private List<PriceMedService> theMedServices;
	/**
	 * Название
	 */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	/**
	 * Название
	 */
	private String theName;
	/**
	 * Код
	 */
	@Comment("Код")
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}
	/**
	 * Код
	 */
	private String theCode;
	/**
	 * Цена
	 */
	@Comment("Цена")
	public BigDecimal getCost() {return theCost;}
	public void setCost(BigDecimal aCost) {theCost = aCost;}
	/**
	 * Цена
	 */
	private BigDecimal theCost;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
	/**
	 * Дата начала действия
	 */
	private Date theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}
	/**
	 * Дата окончания действия
	 */
	private Date theDateTo;
	
	/** Ед.измерения */
	@Comment("Ед.измерения")
	public String getExpUnit() {return theExpUnit;}
	public void setExpUnit(String aExpUnit) {theExpUnit = aExpUnit;}

	/** Код экспорта */
	@Comment("Код экспорта")
	public String getExpParentCode() {return theExpParentCode;}
	public void setExpParentCode(String aExpCode) {theExpParentCode = aExpCode;}

	/** Группа */
	@Comment("Группа")
	@OneToOne
	public PricePosition getParent() {return theParent;}
	public void setParent(PricePosition aPriceGroup) {theParent = aPriceGroup;}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарий */
	private String theComment;
	/** Группа */
	private PricePosition theParent;
	/** Код экспорта */
	private String theExpParentCode;
	/** Ед.измерения */
	private String theExpUnit;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** С НДС */
	@Comment("С НДС")
	public Boolean getIsVat() {return theIsVat;}
	public void setIsVat(Boolean aIsVat) {theIsVat = aIsVat;}

	/** С НДС */
	private Boolean theIsVat;
	/** Отображать на инфомате */
	@Comment("Отображать на инфомате")
	public Boolean getIsViewInfomat() {return theIsViewInfomat;}
	public void setIsViewInfomat(Boolean aIsViewInfomat) {theIsViewInfomat = aIsViewInfomat;}

	/** Отображать на инфомате */
	private Boolean theIsViewInfomat;
	
	/** Тип услуги */
	@Comment("Тип услуги")
	@OneToOne
	public VocPositionType getPositionType() {return thePositionType;}
	public void setPositionType(VocPositionType aPositionType) {thePositionType = aPositionType;}

	/** Тип услуги */
	private VocPositionType thePositionType;
	/**
	 * НДС
	 */
	@Comment("Цена")
	public BigDecimal getCostVat() {return theCostVat;}
	public void setCostVat(BigDecimal aCostVat) {theCostVat = aCostVat;}
	/**
	 * НДС
	 */
	private BigDecimal theCostVat;
	
	/** Примечание для печати */
	@Comment("Примечание для печати")
	@Column(length= 1000)
	public String getPrintComment() {return thePrintComment;}
	public void setPrintComment(String aPrintComment) {thePrintComment = aPrintComment;}

	/** Примечание для печати */
	private String thePrintComment;
	
	/** Ставка налога */
	@Comment("Ставка налога")
	@OneToOne
	public VocVat getTax() {return theTax;}
	public void setTax(VocVat aTax) {theTax = aTax;}
	/** Ставка налога */
	private VocVat theTax;

	}
