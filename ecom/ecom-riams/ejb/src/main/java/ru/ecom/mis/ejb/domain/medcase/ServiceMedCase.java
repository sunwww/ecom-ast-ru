package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Услуга как случай медицинского обслуживания
 * @author oegorova
 *
 */
@Comment("Услуга как случай медицинского обслуживания")
@Entity
@Getter
@Setter
public class ServiceMedCase extends ShortMedCase {
	/** Номер направления */
	private String orderNumber;

	/** Мед. услуга */
	@Comment("Мед. услуга")
	@OneToOne
	public MedService getMedService() {
		return medService;
	}
	/** Мед. услуга */
	private MedService medService;
	
	/** Количество мед. услуг */
	private Integer medServiceAmount;
	
	@Transient
	public String getMedServiceInfo() {
		return medService!=null? medService.getName() :"";
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

	/** Комментарий по услуге */
	@Comment("Комментарий по услуге")
	public String getServiceComment() {
		return serviceComment;
	}
	/** Комментарий по услуге */
	private String serviceComment;
}
