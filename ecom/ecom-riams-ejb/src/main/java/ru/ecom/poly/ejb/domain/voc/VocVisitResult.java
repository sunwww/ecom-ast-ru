package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


//Справочник Результатов обращений
@Entity
@Table(schema="SQLUser")
public class VocVisitResult extends VocIdNameOmcCode {
	/** Код федеральный по поликлинике */
	@Comment("Код федеральный по поликлинике")
	public String getCodefpl() {return theCodefpl;}
	public void setCodefpl(String aCodefpl) {theCodefpl = aCodefpl;}

	/** Код федеральный по поликлинике */
	private String theCodefpl;
}
