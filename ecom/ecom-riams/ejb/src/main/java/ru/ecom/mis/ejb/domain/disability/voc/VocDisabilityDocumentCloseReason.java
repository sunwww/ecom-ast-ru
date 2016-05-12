package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Причина закрытия документа нетрудоспособности
 * @author azviagin
 *
 */
@Comment("Причина закрытия документа нетрудоспособности")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "is16AtWork" }) })
public class VocDisabilityDocumentCloseReason extends VocBaseEntity{
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

	/** Краткое название */
	@Comment("Краткое название")
	@AFormatFieldSuggest({"SHORTNAME"})
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Трудоспособен по 16 форме */
	@Comment("Трудоспособен по 16 форме")
	public Boolean getIs16AtWork() {return theIs16AtWork;}
	public void setIs16AtWork(Boolean aIs16AtWork) {theIs16AtWork = aIs16AtWork;}

	/** Трудоспособен по 16 форме */
	private Boolean theIs16AtWork;
	/** Краткое название */
	private String theShortName;
	/** Не действует */
	private Boolean theDisable;
	/** Федеральный код */
	private String theCodeF;
}
