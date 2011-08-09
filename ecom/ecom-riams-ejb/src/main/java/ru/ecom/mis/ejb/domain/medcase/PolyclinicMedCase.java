package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Случай поликлинического обслуживания
 * @author oegorova
 *
 */
@Comment("Случай поликлинического обслуживания")
@Entity
@Table(schema="SQLUser")
public class PolyclinicMedCase extends LongMedCase{
	/** Количество визитов */
	@Comment("Количество визитов")
	@Transient
	public Integer getVisitsCount() {
		return getChildMedCase().size();	
	}
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Случай поликлинического обслуживания ").append(getId()) ;
		return ret.toString() ;
	}
}
