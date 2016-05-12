package ru.ecom.mis.ejb.domain.contract.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник разрешений по договорному правилу (разрешено, запрещено, разрешено с согласованием)
  */
 @Comment("Справочник разрешений по договорному правилу (разрешено, запрещено, разрешено с согласованием)")
@Entity
@Table(schema="SQLUser")
public class VocContractPermission extends VocBaseEntity{
}
