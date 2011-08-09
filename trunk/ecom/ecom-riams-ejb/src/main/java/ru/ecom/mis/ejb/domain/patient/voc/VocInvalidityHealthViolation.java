package ru.ecom.mis.ejb.domain.patient.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник главного нарушения состояния здоровья при инвалидности
  */
 @Comment("Справочник главного нарушения состояния здоровья при инвалидности")
@Entity
@Table(schema="SQLUser")
public class VocInvalidityHealthViolation extends VocBaseEntity{
}
