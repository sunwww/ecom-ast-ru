package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Вид решения суда по принудительному психиатрическому лечению
  */
 @Comment("Вид решения суда по принудительному психиатрическому лечению")
@Entity
@Table(schema="SQLUser")
public class VocPsychCourtTreatmentDecision extends VocBaseEntity{
}
