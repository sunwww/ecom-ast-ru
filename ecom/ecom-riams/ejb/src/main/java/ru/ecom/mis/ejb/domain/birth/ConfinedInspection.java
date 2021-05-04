package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.birth.voc.VocInspectionCondition;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Осмотр родильницы
 * @author azviagin
 *
 */

@Comment("Осмотр родильницы")
@Entity
@Getter
@Setter
public class ConfinedInspection extends Inspection {
	
	/** Общее состояние */
	@Comment("Общее состояние")
	@OneToOne
	public VocInspectionCondition getCondition() {return condition;}

	@Transient
	public String getInformation() {
		return "Общее состояние: " + condition;
	}
	@Transient
	public String getTypeInformation() {
		return  "Осмотр родившей";
	}	
	/** Общее состояние */
	private VocInspectionCondition condition;
	/** Состояние молочных желез */
	private String mammariesCondition;
	/** Высота матки */
	private Integer uterusHeight;
	/** Лохии */
	private String lochia;
	/** Функции мочевого пузыря */
	private String urinaryBladderFunctions;
	/** Функции кишечника */
	private String intestinesFunctions;
	/** Назначения */
	private String prescriptions;
}