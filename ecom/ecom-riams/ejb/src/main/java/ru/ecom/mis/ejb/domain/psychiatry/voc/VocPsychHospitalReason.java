package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник причин госпитализации в психиатрический стационар
  */
 @Comment("Справочник причин госпитализации в психиатрический стационар")
@Entity
@Table(schema="SQLUser")
public class VocPsychHospitalReason extends VocBaseEntity{
}
