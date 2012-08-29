package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник причин начала психиатрического наблюдения
  */
 @Comment("Справочник причин начала психиатрического наблюдения")
@Entity
@Table(schema="SQLUser")
public class VocPsychObservationReason extends VocBaseEntity{
	 /** Первичная */
	@Comment("Первичная")
	public Boolean getIsPrimary() {
		return theIsPrimary;
	}

	public void setIsPrimary(Boolean aIsPrimary) {
		theIsPrimary = aIsPrimary;
	}

	/** Первичная */
	private Boolean theIsPrimary;
}
