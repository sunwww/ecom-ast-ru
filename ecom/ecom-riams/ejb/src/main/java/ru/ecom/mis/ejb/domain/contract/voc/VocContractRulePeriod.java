package ru.ecom.mis.ejb.domain.contract.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник периодов договорного правила
  */
 @Comment("Справочник периодов договорного правила")
@Entity
@Table(schema="SQLUser")
public class VocContractRulePeriod extends VocBaseEntity{
}
