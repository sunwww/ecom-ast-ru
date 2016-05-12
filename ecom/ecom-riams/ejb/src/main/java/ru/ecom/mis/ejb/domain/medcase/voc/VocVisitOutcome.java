package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник исходов визита")
@Table(schema="SQLUser")
public class VocVisitOutcome extends VocBaseEntity {
	/** Код фед. поликлинический */
	@Comment("Код фед. поликлинический")
	public String getCodefpl() {return theCodefpl;}
	public void setCodefpl(String aCodefpl) {theCodefpl = aCodefpl;}

	/** Код фед. ск.помощи */
	@Comment("Код фед. ск.помощи")
	public String getCodefamb() {return theCodefamb;}
	public void setCodefamb(String aCodefamb) {theCodefamb = aCodefamb;}

	/** Код фед. ск.помощи */
	private String theCodefamb;
	/** Код фед. поликлинический */
	private String theCodefpl;
}
