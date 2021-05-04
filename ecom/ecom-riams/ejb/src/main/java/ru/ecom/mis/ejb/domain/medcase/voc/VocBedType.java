package ru.ecom.mis.ejb.domain.medcase.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Comment("Справочник. Профиль коек")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocBedType extends VocBaseEntity{

	/** Профиль медицинской помощи */
	@Comment("Профиль медицинской помощи")
	@OneToOne
	public VocE2MedHelpProfile getMedHelpProfile() {return medHelpProfile;}
	/** Профиль медицинской помощи */
	private VocE2MedHelpProfile medHelpProfile ;

	/** Код федеральный стационар (для детей) */
	private String codeFC;
	/** Код федеральный по справочнику V020 */
	private String codeF;
	/** ОМС код */
	private String omcCode;
}