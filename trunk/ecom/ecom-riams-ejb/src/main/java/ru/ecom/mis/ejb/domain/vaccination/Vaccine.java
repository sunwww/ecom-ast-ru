package ru.ecom.mis.ejb.domain.vaccination;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.vaccination.voc.VaccineVocLocalReaction;
import ru.ecom.mis.ejb.domain.vaccination.voc.VaccineVocMethod;
import ru.ecom.mis.ejb.domain.vaccination.voc.VaccineVocNosology;
import ru.ecom.mis.ejb.domain.vaccination.voc.VaccineVocCommonReaction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Вакцина
 * @author azviagin
 *
 */
@Entity
@Comment("Вакцина")
@Table(schema="SQLUser")
public class Vaccine extends BaseEntity {

	/**
	 * Название
	 */
	@Comment("Название")
	public String getName() {
		return theName;
	}

	/**
	 * Название
	 */
	public void setName(String a_Property) {
		theName = a_Property;
	}

	/**
	 * Название
	 */
	private String theName;

	/**
	 * Аббревиатура
	 */
	@Comment("Аббревиатура")
	public String getAbbrevation() {
		return theAbbrevation;
	}

	/**
	 * Аббревиатура
	 */
	public void setAbbrevation(String a_Property) {
		theAbbrevation = a_Property;
	}

	/**
	 * Аббревиатура
	 */
	private String theAbbrevation;

	/**
	 * Список вакцинируемых нозологий
	 */
	@Comment("Список вакцинируемых нозологий")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocNosology> getNosologyList() {
		return theNosologyList;
	}

	/**
	 * Список вакцинируемых нозологий
	 */
	public void setNosologyList(List<VaccineVocNosology> a_Property) {
		theNosologyList = a_Property;
	}

	/**
	 * Список вакцинируемых нозологий
	 */
	private List<VaccineVocNosology> theNosologyList;

	/**
	 * Список методов вакцинации
	 */
	@Comment("Список методов вакцинации")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocMethod> getMethodList() {
		return theMethodList;
	}

	/**
	 * Список методов вакцинации
	 */
	public void setMethodList(List<VaccineVocMethod> a_Property) {
		theMethodList = a_Property;
	}

	/**
	 * Список методов вакцинации
	 */
	private List<VaccineVocMethod> theMethodList;

	/**
	 * Список общих реакций
	 */
	@Comment("Список общих реакций")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocCommonReaction> getCommonReactionList() {
		return theCommonReactionList;
	}

	/**
	 * Список общих реакций
	 */
	public void setCommonReactionList(List<VaccineVocCommonReaction> a_Property) {
		theCommonReactionList = a_Property;
	}

	/**
	 * Список общих реакций
	 */
	private List<VaccineVocCommonReaction> theCommonReactionList;

	/**
	 * Список местных реакций
	 */
	
	@Comment("Список местных реакций")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocLocalReaction> getLocalReactionList() {
		return theLocalReactionList;
	}

	/**
	 * Список местных реакций
	 */
	public void setLocalReactionList(List<VaccineVocLocalReaction> a_Property) {
		theLocalReactionList = a_Property;
	}

	/**
	 * Список местных реакций
	 */
	private List<VaccineVocLocalReaction> theLocalReactionList;

	

}
