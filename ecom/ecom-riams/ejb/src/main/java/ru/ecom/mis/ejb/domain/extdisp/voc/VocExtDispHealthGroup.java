package ru.ecom.mis.ejb.domain.extdisp.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Справочник групп здоровья дополнительной диспансеризации
	 */
	@Comment("Справочник групп здоровья дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
	@Getter
	@Setter
public class VocExtDispHealthGroup extends VocBaseEntity{
	/**
	 * Номер строки карты дополнительной диспансеризации
	 */
	private String cardStringNumber;
	/**
	 * Тип дополнительной диспансеризации
	 */
	@Comment("Тип дополнительной диспансеризации")
	@OneToOne
	public VocExtDisp getDispType() {return dispType;}
	/**
	 * Тип дополнительной диспансеризации
	 */
	private VocExtDisp dispType;

	/** Код диспансеризации */
	private String dispCode;
	
	/** Архивная */
	private Boolean isArchival;
	
	/** Срок действия */
	private Long validityDays;
}
