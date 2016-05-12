package ru.ecom.mis.ejb.domain.contract.voc;
import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * 
  */
 @Comment("Справочник обработки правил")
@Entity
@Table(schema="SQLUser")
public class VocContractRulesProcessing extends VocBaseEntity{
}
