package ru.ecom.mis.ejb.domain.disability.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocRegimeViolationType extends VocBaseEntity{
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
