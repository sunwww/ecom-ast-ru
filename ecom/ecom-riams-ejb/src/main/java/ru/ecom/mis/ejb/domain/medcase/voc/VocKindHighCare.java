package ru.ecom.mis.ejb.domain.medcase.voc;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocKindHighCare extends VocBaseEntity {
	/** Код потока обслуживания */
	@Comment("Код потока обслуживания")
	public String getServiceStreamCode() {return theServiceStreamCode;}
	public void setServiceStreamCode(String aServiceStreamCode) {theServiceStreamCode = aServiceStreamCode;}

	/** Код потока обслуживания */
	private String theServiceStreamCode;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** Дата начала */
	@Comment("Дата начала")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	/** Дата начала */
	private Date theDateFrom;
	/** Дата окончания */
	private Date theDateTo;
}
