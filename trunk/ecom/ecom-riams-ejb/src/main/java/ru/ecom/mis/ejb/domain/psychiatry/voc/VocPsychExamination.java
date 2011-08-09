package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник видов психиатрических экспертиз
  */
 @Comment("Справочник видов психиатрических экспертиз")
@Entity
@Table(schema="SQLUser")
public class VocPsychExamination extends VocBaseEntity{
}
