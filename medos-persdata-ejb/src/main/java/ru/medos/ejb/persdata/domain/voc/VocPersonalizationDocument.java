package ru.medos.ejb.persdata.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник документов для персонализации
	 */
	@Comment("Справочник документов для персонализации")
@Entity
@Table(schema="SQLUser")
public class VocPersonalizationDocument extends VocBaseEntity{
}
