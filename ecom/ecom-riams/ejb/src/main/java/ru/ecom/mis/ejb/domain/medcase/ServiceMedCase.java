package ru.ecom.mis.ejb.domain.medcase;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Услуга как случай медицинского обслуживания
 * @author oegorova
 *
 */
@Comment("Услуга как случай медицинского обслуживания")
@Entity
@Table(schema="SQLUser")
public class ServiceMedCase extends ShortMedCase{
	/** Номер направления */
	@Comment("Номер направления")
	public String getOrderNumber() {return theOrderNumber;}
	public void setOrderNumber(String aOrderNumber) {theOrderNumber = aOrderNumber;}
	/** Номер направления */
	private String theOrderNumber;

	/** Мед. услуга */
	@Comment("Мед. услуга")
	@OneToOne
	public MedService getMedService() {
		return theMedService;
	}

	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}

	/** Мед. услуга */
	private MedService theMedService;
	
	/** Количество мед. услуг */
	@Comment("Количество мед. услуг")
	public Integer getMedServiceAmount() {
		return theMedServiceAmount;
	}

	public void setMedServiceAmount(Integer aMedServiceAmount) {
		theMedServiceAmount = aMedServiceAmount;
	}

	

	/** Количество мед. услуг */
	private Integer theMedServiceAmount;
	
	@Transient
	public String getMedServiceInfo() {
		return theMedService!=null? theMedService.getName() :"";
	}
	
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Услуга ").append(getMedServiceInfo()) ;
		if(getParent()!=null) {
			ret.append(" по ").append(getParent().getInfo()) ;
		}
		
		return ret.toString() ;
	}
	
	@Transient
	public String getCategoryMedServiceInfo() {
		return getMedService()!=null ? getMedService().getCategoryInfo():"";
	}
	@Transient
	public MedService getCategoryMedService() {
		return getMedService()!=null ? getMedService().getParent():null;
	}


}
