package ru.ecom.mis.ejb.domain.contract.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник операций договорного счета
  */
 @Comment("Справочник операций договорного счета")
@Entity
@Table(schema="SQLUser")
public class VocAccountOperation extends VocBaseEntity{
	 /** Красная проводка */
	@Comment("Красная проводка")
	@OneToOne
	public VocAccountOperation getRedOperation() {return theRedOperation;}
	public void setRedOperation(VocAccountOperation aRedOperation) {theRedOperation = aRedOperation;}

	/** Красная проводка */
	private VocAccountOperation theRedOperation;
}
