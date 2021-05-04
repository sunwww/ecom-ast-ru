package ru.ecom.mis.ejb.domain.contract.voc;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.contract.JuridicalPerson;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
 /**
  * Справочник программ обслуживания
  */
 @Comment("Справочник программ обслуживания")
@Entity
@Table(schema="SQLUser")
 @Getter
 @Setter
public class VocServiceProgram extends VocBaseEntity{
	 /** Юридическая персона */
	 @OneToOne
	@Comment("Юридическая персона")
	public JuridicalPerson getPerson() {
		return person;
	}
	/** Юридическая персона */
	private JuridicalPerson person;
}
