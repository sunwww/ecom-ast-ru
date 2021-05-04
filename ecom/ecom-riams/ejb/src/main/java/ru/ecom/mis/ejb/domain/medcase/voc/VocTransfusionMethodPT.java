package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Метод по инд.совместимость")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocTransfusionMethodPT extends VocBaseEntity {
	/** Реактив */
	private String reagent;

	/** Не действует */
	@Comment("Не действует")
	@AFormatFieldSuggest({"DISABLE"})
	public Boolean getDisable() {return disable;}
	private Boolean disable;
}
