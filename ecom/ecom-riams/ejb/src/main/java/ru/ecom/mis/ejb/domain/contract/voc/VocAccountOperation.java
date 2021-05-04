package ru.ecom.mis.ejb.domain.contract.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник операций договорного счета
  */
 @Comment("Справочник операций договорного счета")
@Entity
@Table(schema="SQLUser")
 @Getter
 @Setter
public class VocAccountOperation extends VocBaseEntity{
	 /** Красная проводка */
	@Comment("Красная проводка")
	@OneToOne
	public VocAccountOperation getRedOperation() {return redOperation;}
	/** Красная проводка */
	private VocAccountOperation redOperation;
}
