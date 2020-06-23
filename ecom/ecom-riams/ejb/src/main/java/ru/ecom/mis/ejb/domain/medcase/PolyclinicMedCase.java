package ru.ecom.mis.ejb.domain.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Случай поликлинического обслуживания
 * @author oegorova
 *
 */
@Comment("Случай поликлинического обслуживания")
@Entity
public class PolyclinicMedCase extends LongMedCase {
	/** Количество визитов */
	@Comment("Количество визитов")
	@Transient
	public Integer getVisitsCount() {
		return 0 ;
	}
	@Transient
	public String getInfo() {
		return "Случай поликлинического обслуживания " + getId();
	}
}
