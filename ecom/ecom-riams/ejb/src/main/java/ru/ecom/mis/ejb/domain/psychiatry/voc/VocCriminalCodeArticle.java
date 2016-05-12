package ru.ecom.mis.ejb.domain.psychiatry.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник статей уголовного кодекса
  */
 @Comment("Справочник статей уголовного кодекса")
@Entity
@Table(schema="SQLUser")
public class VocCriminalCodeArticle extends VocBaseEntity{
}
