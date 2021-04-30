package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник групп психиатрического диспансерного наблюдения
  */
 @Comment("Справочник групп психиатрического диспансерного наблюдения")
@Entity
@Table(schema="SQLUser")
 @Getter
 @Setter
public class VocPsychDispensaryGroup extends VocBaseEntity{
	 /** Вид наблюдения */
	@Comment("Вид наблюдения")
	@OneToOne
	public VocPsychAmbulatoryCare getType() {
		return type;
	}

	/** Вид наблюдения */
	private VocPsychAmbulatoryCare type;
}
