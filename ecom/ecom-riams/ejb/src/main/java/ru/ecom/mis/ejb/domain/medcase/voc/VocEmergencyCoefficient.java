package ru.ecom.mis.ejb.domain.medcase.voc;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcLpu;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Коэффициент экстренности
 * @author azviagin
 *
 */
@Comment("Коэффициент экстренности")
@Entity
@Table(schema="SQLUser")
public class VocEmergencyCoefficient extends BaseEntity{
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public OmcLpu getLpu() {
		return theLpu;
	}

	public void setLpu(OmcLpu aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private OmcLpu theLpu;
	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getDateFrom() {
		return theDateFrom;
	}

	public void setDateFrom(Date aDateFrom) {
		theDateFrom = aDateFrom;
	}

	/** Дата начала действия */
	private Date theDateFrom;
	
	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getDateTo() {
		return theDateTo;
	}

	public void setDateTo(Date aDateTo) {
		theDateTo = aDateTo;
	}

	/** Дата окончания действия */
	private Date theDateTo;
	
	/** Коэффициент */
	@Comment("Коэффициент")
	public Double getCoefficient() {
		return theCoefficient;
	}

	public void setCoefficient(Double aCoefficient) {
		theCoefficient = aCoefficient;
	}

	/** Коэффициент */
	private Double theCoefficient;

}
