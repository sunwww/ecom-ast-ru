package ru.ecom.mis.ejb.domain.vaccination;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Vaccine extends BaseEntity {

	/**
	 * Название
	 */
	private String name;


	/**
	 * Аббревиатура
	 */
	private String abbrevation;

	/**
	 * Список вакцинируемых нозологий
	 */
	@Comment("Список вакцинируемых нозологий")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocNosology> getNosologyList() {
		return nosologyList;
	}

	/**
	 * Список вакцинируемых нозологий
	 */
	private List<VaccineVocNosology> nosologyList;

	/**
	 * Список методов вакцинации
	 */
	@Comment("Список методов вакцинации")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocMethod> getMethodList() {
		return methodList;
	}

	/**
	 * Список методов вакцинации
	 */
	private List<VaccineVocMethod> methodList;

	/**
	 * Список общих реакций
	 */
	@Comment("Список общих реакций")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocCommonReaction> getCommonReactionList() {
		return commonReactionList;
	}

	/**
	 * Список общих реакций
	 */
	private List<VaccineVocCommonReaction> commonReactionList;

	/**
	 * Список местных реакций
	 */
	
	@Comment("Список местных реакций")
	@OneToMany(mappedBy="vaccine", cascade=CascadeType.ALL)
	public List<VaccineVocLocalReaction> getLocalReactionList() {
		return localReactionList;
	}

	/**
	 * Список местных реакций
	 */
	private List<VaccineVocLocalReaction> localReactionList;

	

}
