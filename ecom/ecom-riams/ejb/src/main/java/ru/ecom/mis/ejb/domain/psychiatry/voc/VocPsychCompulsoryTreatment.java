package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник типов принудительного психиатрического лечения
  */
 @Comment("Справочник типов принудительного психиатрического лечения")
@Entity
@Table(schema="SQLUser")
 @Getter
 @Setter
public class VocPsychCompulsoryTreatment extends VocBaseEntity{
	/** Окончание */
	private Boolean isFinish;
}
