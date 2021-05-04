package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
	 * Интервал нозологий (A01-B99, G10.1-G10.7, Z
	 */
	@Comment("Интервал нозологий (A01-B99, G10.1-G10.7, Z")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties = {"nosologyGroup"})

	})
	@Getter
	@Setter
public class NosologyInterval extends BaseEntity{
	/**
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@ManyToOne
	public ContractNosologyGroup getNosologyGroup() {
		return nosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private ContractNosologyGroup nosologyGroup;
	/**
	 * Начиная с код МКБ10
	 */
	private String fromIdc10Code;
	/**
	 * Заканчивая кодом МКБ10
	 */
	private String toIdc10Code;

	/**
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@OneToOne
	public VocIdc10 getFromCode() {
		return fromCode;
	}
	/**
	 * Начиная с кода
	 */
	private VocIdc10 fromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@OneToOne
	public VocIdc10 getToCode() {
		return toCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private VocIdc10 toCode;
	
	/**
	 * Название
	 */
	private String name;
}
