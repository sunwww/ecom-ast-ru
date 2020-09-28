package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Лист назначений
 * @author azviagin
 *
 */

@Comment("Лист назначений")
@Entity
@EntityListeners(DeleteListener.class)
public class PrescriptList extends AbstractPrescriptionList {

	@PrePersist
	void onPrePersist() {
		long currentTime = System.currentTimeMillis();
		setCreateDate(new java.sql.Date(currentTime));
		setCreateTime(new java.sql.Time(currentTime));
	}
	
	/** Шаблон листа назначений */
	@Comment("Шаблон листа назначений")
	@OneToOne
	public PrescriptListTemplate getTemplate() {
		return theTemplate;
	}

	public void setTemplate(PrescriptListTemplate aTemplate) {
		theTemplate = aTemplate;
	}
	
	/** Период */
	@Comment("Дата начала-дата окончания")
	@Transient
	public String getPeriodActual() {
		Date startDate = null ;
		Date endDate = null ;
		for (Prescription pres:getPrescriptions()) {
			if (pres.getPlanEndDate()!=null ) {
				if ((endDate==null) || ( pres.getPlanEndDate().getTime()>endDate.getTime())) {
					endDate=pres.getPlanEndDate() ;
				}
			}
			if (pres.getPlanStartDate()!=null ) {
				if ((startDate==null) || (endDate!=null && pres.getPlanStartDate().getTime()>startDate.getTime())) {
					startDate=pres.getPlanStartDate() ;
				}
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		StringBuilder ret = new StringBuilder() ;
		if (startDate==null) {
			ret.append("нет даты начала") ;
		} else {
			ret.append(format.format(startDate)) ;
		}
		ret.append("-") ;
		if (endDate==null) {
			ret.append("нет даты окончания") ;
		} else {
			ret.append(format.format(endDate)) ;
		}
		return  ret.toString() ;
	}

	/** Шаблон листа назначений */
	private PrescriptListTemplate theTemplate;

}
