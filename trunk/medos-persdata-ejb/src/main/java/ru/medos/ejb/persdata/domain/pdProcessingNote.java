package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справка по обработке персональных данных
	 */
	@Comment("Справка по обработке персональных данных")
@Entity
@Table(schema="SQLUser")
public class pdProcessingNote extends OutgoingDocument{
}
