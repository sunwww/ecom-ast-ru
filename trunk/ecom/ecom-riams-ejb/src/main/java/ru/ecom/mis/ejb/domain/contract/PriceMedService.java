package ru.ecom.mis.ejb.domain.contract;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.contract.PricePosition;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Медицинская услуга прескуранта  @author azviagin
	 */
	@Comment("Медицинская услуга прескуранта")
@Entity
@Table(schema="SQLUser")
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
	/**
	 * Позиция прейскуранта
	 */
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
		return theMedService!=null? theMedService.getName():"" ;
	}
}
