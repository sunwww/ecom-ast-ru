package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocDisabilityDocumentCloseReason extends VocBaseEntity{
	/** Федеральный код */
	@Comment("Федеральный код")
	@AFormatFieldSuggest({"CODEF"})
	public String getCodeF() {return codeF;}

	/** Не действует */
	@Comment("Не действует")
	@AFormatFieldSuggest({"DISABLE"})
	public Boolean getDisable() {return disable;}

	/** Краткое название */
	@Comment("Краткое название")
	@AFormatFieldSuggest({"SHORTNAME"})
	public String getShortName() {return shortName;}

	/** Трудоспособен по 16 форме */
	@Comment("Трудоспособен по 16 форме")
	public Boolean getIs16AtWork() {return is16AtWork;}

	/** Трудоспособен по 16 форме */
	private Boolean is16AtWork;
	/** Краткое название */
	private String shortName;
	/** Не действует */
	private Boolean disable;
	/** Федеральный код */
	private String codeF;
}
