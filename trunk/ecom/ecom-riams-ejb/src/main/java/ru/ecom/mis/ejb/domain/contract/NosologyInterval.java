package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.contract.ContractNosologyGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;

	/**
	 * Интервал нозологий (A01-B99, G10.1-G10.7, Z
	 */
	@Comment("Интервал нозологий (A01-B99, G10.1-G10.7, Z")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(unique= false, properties = {"name"})
	})
public class NosologyInterval extends BaseEntity{
	/**
	 * Нозологическая группа
	 */
	@Comment("Нозологическая группа")
	@ManyToOne
	public ContractNosologyGroup getNosologyGroup() {
		return theNosologyGroup;
	}
	public void setNosologyGroup(ContractNosologyGroup aNosologyGroup) {
		theNosologyGroup = aNosologyGroup;
	}
	/**
	 * Нозологическая группа
	 */
	private ContractNosologyGroup theNosologyGroup;
	/**
	 * Начиная с код МКБ10
	 */
	@Comment("Начиная с код МКБ10")
	
	public String getFromIdc10Code() {
		return theFromIdc10Code;
	}
	public void setFromIdc10Code(String aFromIdc10Code) {
		theFromIdc10Code = aFromIdc10Code;
	}
	/**
	 * Начиная с код МКБ10
	 */
	private String theFromIdc10Code;
	/**
	 * Заканчивая кодом МКБ10
	 */
	@Comment("Заканчивая кодом МКБ10")
	
	public String getToIdc10Code() {
		return theToIdc10Code;
	}
	public void setToIdc10Code(String aToIdc10Code) {
		theToIdc10Code = aToIdc10Code;
	}
	/**
	 * Заканчивая кодом МКБ10
	 */
	private String theToIdc10Code;

	/**
	 * Начиная с кода
	 */
	@Comment("Начиная с кода")
	@OneToOne
	public VocIdc10 getFromCode() {
		return theFromCode;
	}
	public void setFromCode(VocIdc10 aFromCode) {
		theFromCode = aFromCode;
	}
	/**
	 * Начиная с кода
	 */
	private VocIdc10 theFromCode;
	/**
	 * Заканчивая кодом
	 */
	@Comment("Заканчивая кодом")
	@OneToOne
	public VocIdc10 getToCode() {
		return theToCode;
	}
	public void setToCode(VocIdc10 aToCode) {
		theToCode = aToCode;
	}
	/**
	 * Заканчивая кодом
	 */
	private VocIdc10 theToCode;
	
	/**
	 * Название
	 */
	@Comment("Название")
	
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
}
