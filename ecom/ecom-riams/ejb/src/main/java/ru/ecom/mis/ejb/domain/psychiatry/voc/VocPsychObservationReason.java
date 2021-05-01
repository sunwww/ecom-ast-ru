package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник причин начала психиатрического наблюдения
  */
 @Comment("Справочник причин начала психиатрического наблюдения")
@Entity
@Table(schema="SQLUser")
 @Getter
 @Setter
public class VocPsychObservationReason extends VocBaseEntity{
	/** Первичная */
	private Boolean isPrimary;
}
