package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник типов общественно опасных действий
  */
 @Comment("Справочник типов общественно опасных действий")
@Entity
@Table(schema="SQLUser")
public class VocPublicDangerousEffect extends VocBaseEntity{
}
