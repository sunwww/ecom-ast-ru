package ru.ecom.mis.ejb.domain.birth;

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
public class ConfinedInspection extends Inspection {
	
	/** Общее состояние */
	@Comment("Общее состояние")
	@OneToOne
	public VocInspectionCondition getCondition() {return theCondition;}
	public void setCondition(VocInspectionCondition aCondition) {theCondition = aCondition;}

	/** Состояние молочных желез */
	@Comment("Состояние молочных желез")
	public String getMammariesCondition() {return theMammariesCondition;}
	public void setMammariesCondition(String aMammariesCondition) {theMammariesCondition = aMammariesCondition;}

	/** Высота матки */
	@Comment("Высота матки")
	public Integer getUterusHeight() {return theUterusHeight;}
	public void setUterusHeight(Integer aUterusHeight) {theUterusHeight = aUterusHeight;}

	/** Лохии */
	@Comment("Лохии")
	public String getLochia() {return theLochia;}
	public void setLochia(String aLochia) {theLochia = aLochia;}

	/** Функции мочевого пузыря */
	@Comment("Функции мочевого пузыря")
	public String getUrinaryBladderFunctions() {return theUrinaryBladderFunctions;}
	public void setUrinaryBladderFunctions(String aUrinaryBladderFunctions) {theUrinaryBladderFunctions = aUrinaryBladderFunctions;}

	/** Функции кишечника */
	@Comment("Функции кишечника")
	public String getIntestinesFunctions() {return theIntestinesFunctions;}
	public void setIntestinesFunctions(String aIntestinesFunctions) {theIntestinesFunctions = aIntestinesFunctions;}

	/** Назначения */
	@Comment("Назначения")
	public String getPrescriptions() {return thePrescriptions;}
	public void setPrescriptions(String aPrescriptions) {thePrescriptions = aPrescriptions;}
	
	@Transient
	public String getInformation() {
		return "Общее состояние: " + theCondition;
	}
	@Transient
	public String getTypeInformation() {
		return  "Осмотр родившей";
	}	
	/** Общее состояние */
	private VocInspectionCondition theCondition;
	/** Состояние молочных желез */
	private String theMammariesCondition;
	/** Высота матки */
	private Integer theUterusHeight;
	/** Лохии */
	private String theLochia;
	/** Функции мочевого пузыря */
	private String theUrinaryBladderFunctions;
	/** Функции кишечника */
	private String theIntestinesFunctions;
	/** Назначения */
	private String thePrescriptions;
}