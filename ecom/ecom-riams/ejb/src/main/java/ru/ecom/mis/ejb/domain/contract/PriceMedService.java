package ru.ecom.mis.ejb.domain.contract;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
	/**
	 * Медицинская услуга прескуранта  @author azviagin
	 */
	@Comment("Медицинская услуга прескуранта")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"pricePosition"})
	, @AIndex(properties = {"medService"})
})
public class PriceMedService extends BaseEntity{
	/**
	 * Позиция прейскуранта
	 */
	@Comment("Позиция прейскуранта")
	@ManyToOne
	public PricePosition getPricePosition() {
		return thePricePosition;
	}
	public void setPricePosition(PricePosition aPricePosition) {
		thePricePosition = aPricePosition;
	}
	private PricePosition thePricePosition;
	/**
	 * Медицинская услуга
	 */
	@Comment("Медицинская услуга")
	@OneToOne
	public MedService getMedService() {
		return theMedService;
	}
	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}
	/**
	 * Медицинская услуга
	 */
	private MedService theMedService;
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
	public String getMedServiceInfo() {
		return theMedService!=null? theMedService.getCode()+" "+theMedService.getName():"" ;
	}
}
