package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Код профиля отделения для стационара или специалиста поликлиники
 */
@Entity
@Comment("Код профиля отделения для стационара или специалиста поликлиники")
@Table(schema="SQLUser")
public class VocOmcDepType extends VocIdName {
	/** Код федеральный */
	@Comment("Код федеральный")
	public String getCodef() {return theCodef;}
	public void setCodef(String aCodef) {theCodef = aCodef;}

	/** Код федеральный */
	private String theCodef;
}
