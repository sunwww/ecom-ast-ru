package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocRegimeViolationType extends VocBaseEntity{
	/** Федеральный код */
	@Comment("Федеральный код")
	@AFormatFieldSuggest({"CODEF"})
	public String getCodeF() {return theCodeF;}
	public void setCodeF(String aCodeF) {theCodeF = aCodeF;}

	/** Не действует */
	@Comment("Не действует")
	@AFormatFieldSuggest({"DISABLE"})
	public Boolean getDisable() {return theDisable;}
	public void setDisable(Boolean aDisable) {theDisable = aDisable;}

	/** Не действует */
	private Boolean theDisable;
	/** Федеральный код */
	private String theCodeF;
}
