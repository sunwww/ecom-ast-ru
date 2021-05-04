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
 * Причина нетрудоспособности
 * @author azviagin
 *
 */
@Comment("Причина нетрудоспособности")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "codeF" }) })
@Getter
@Setter
public class VocDisabilityReason extends VocBaseEntity{
	/** Федеральный код */
	@Comment("Федеральный код")
	@AFormatFieldSuggest({"CODEF"})
	public String getCodeF() {return codeF;}

	/** Не действует */
	@Comment("Не действует")
	@AFormatFieldSuggest({"DISABLE"})
	public Boolean getDisable() {return disable;}

	/** Не действует */
	private Boolean disable;
	/** Федеральный код */
	private String codeF;
}
