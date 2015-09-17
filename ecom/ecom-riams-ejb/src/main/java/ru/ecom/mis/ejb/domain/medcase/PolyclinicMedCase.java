package ru.ecom.mis.ejb.domain.medcase;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
		return 0 ;
	}
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Случай поликлинического обслуживания ").append(getId()) ;
		return ret.toString() ;
	}
}
