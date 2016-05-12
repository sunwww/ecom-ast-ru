package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник видов уголовных дел
  */
 @Comment("Справочник видов уголовных дел")
@Entity
@Table(schema="SQLUser")
public class VocCriminalCase extends VocBaseEntity{
}
