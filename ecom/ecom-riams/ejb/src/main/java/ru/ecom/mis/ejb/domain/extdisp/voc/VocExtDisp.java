package ru.ecom.mis.ejb.domain.extdisp.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

	/**
	 * Справочник видов дополнительной диспансеризации
	 */
	@Comment("Справочник видов дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
	@Getter
	@Setter
public class VocExtDisp extends VocBaseEntity{
	/** Внутренний код */
	private String internalCode;
	
	/** Флаг медосмотра */
	private Boolean isComission;

	/** Оказывается только прикрепленному населению */
	private Boolean attachmentPopulation;

	/** Запрет на дублирование */
	private Boolean disableAgeDoubles;

	/** В архиве */
	private Boolean isArchival ;

	/** Автоматически расчитывать возраст */
	private Boolean autoCalcAge ;

}
