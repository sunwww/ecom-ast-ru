package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник вида участия в психиатрической экспертизе
  */
 @Comment("Справочник вида участия в психиатрической экспертизе")
@Entity
@Table(schema="SQLUser")
public class VocPsychExamPaticipation extends VocBaseEntity{
}
