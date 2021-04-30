package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
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
	@Getter
	@Setter
public class PriceMedService extends BaseEntity{
	/**
	 * Позиция прейскуранта
	 */
	@Comment("Позиция прейскуранта")
	@ManyToOne
	public PricePosition getPricePosition() {
		return pricePosition;
	}
	private PricePosition pricePosition;
	/**
	 * Медицинская услуга
	 */
	@Comment("Медицинская услуга")
	@OneToOne
	public MedService getMedService() {
		return medService;
	}
	/**
	 * Медицинская услуга
	 */
	private MedService medService;
	/**
	 * Дата начала действия
	 */
	private Date dateFrom;
	/**
	 * Дата окончания действия
	 */
	private Date dateTo;
	@Transient
	public String getMedServiceInfo() {
		return medService!=null? medService.getCode()+" "+medService.getName():"" ;
	}
}
