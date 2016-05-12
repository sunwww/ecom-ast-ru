package ru.ecom.mis.ejb.domain.contract.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник типов цен
  */
 @Comment("Справочник типов цен")
@Entity
@Table(schema="SQLUser")
public class VocPrice extends VocBaseEntity{
}
