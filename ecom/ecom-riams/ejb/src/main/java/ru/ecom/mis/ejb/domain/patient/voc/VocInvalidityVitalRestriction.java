package ru.ecom.mis.ejb.domain.patient.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Ведущее ограничение жизнедеятельности при инвалидности
  */
 @Comment("Ведущее ограничение жизнедеятельности при инвалидности")
@Entity
@Table(schema="SQLUser")
public class VocInvalidityVitalRestriction extends VocBaseEntity{
}
