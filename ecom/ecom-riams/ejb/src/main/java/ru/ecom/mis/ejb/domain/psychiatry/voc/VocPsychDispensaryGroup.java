package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник групп психиатрического диспансерного наблюдения
  */
 @Comment("Справочник групп психиатрического диспансерного наблюдения")
@Entity
@Table(schema="SQLUser")
public class VocPsychDispensaryGroup extends VocBaseEntity{
	 /** Вид наблюдения */
	@Comment("Вид наблюдения")
	@OneToOne
	public VocPsychAmbulatoryCare getType() {
		return theType;
	}

	public void setType(VocPsychAmbulatoryCare aType) {
		theType = aType;
	}

	/** Вид наблюдения */
	private VocPsychAmbulatoryCare theType;
}
