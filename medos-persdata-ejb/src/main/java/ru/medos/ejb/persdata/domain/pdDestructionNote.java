package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справка об удалении персональных данных
	 */
	@Comment("Справка об удалении персональных данных")
@Entity
@Table(schema="SQLUser")
public class pdDestructionNote extends OutgoingDocument{
}
