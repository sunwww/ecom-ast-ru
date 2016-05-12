package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Причина нетрудоспособности
 * @author azviagin
 *
 */
@Comment("Причина нетрудоспособности")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "codeF" }) })
public class VocDisabilityReason extends VocBaseEntity{
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
