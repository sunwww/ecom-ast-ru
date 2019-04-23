package ru.ecom.poly.ejb.domain.voc;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;


//Справочник Результатов обращений
@Entity
@Table(schema="SQLUser")
public class VocVisitResult extends VocIdNameOmcCode {

	/** Код исхода федеральный по поликлинике */
	private String theCodefIshod ;
	/** Код фед. ск.помощи */
	private String theCodefamb;
	/** Код результата федеральный по поликлинике */
	private String theCodefpl;
	/**Код в промеде1**/
	private String promedCode1;

	@Comment("Код исхода федеральный по поликлинике")
	public String getCodefIshod() {return theCodefIshod;}
	public void setCodefIshod(String aCodefIshod) {theCodefIshod = aCodefIshod;}

	@Comment("Код федеральный по поликлинике")
	public String getCodefpl() {return theCodefpl;}
	public void setCodefpl(String aCodefpl) {theCodefpl = aCodefpl;}

	@Comment("Код фед. ск.помощи")
	public String getCodefamb() {return theCodefamb;}
	public void setCodefamb(String aCodefamb) {theCodefamb = aCodefamb;}
}