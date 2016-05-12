package ru.ecom.mis.ejb.domain.patient.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник условий проживания
  */
 @Comment("Справочник условий проживания")
@Entity
@Table(schema="SQLUser")
public class VocResidenceConditions extends VocBaseEntity{
}
