package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник типов принудительного психиатрического лечения
  */
 @Comment("Справочник типов принудительного психиатрического лечения")
@Entity
@Table(schema="SQLUser")
public class VocPsychCompulsoryTreatment extends VocBaseEntity{
	 /** Окончание */
	@Comment("Окончание")
	public Boolean getIsFinish() {
		return theIsFinish;
	}

	public void setIsFinish(Boolean aIsFinish) {
		theIsFinish = aIsFinish;
	}

	/** Окончание */
	private Boolean theIsFinish;
}
