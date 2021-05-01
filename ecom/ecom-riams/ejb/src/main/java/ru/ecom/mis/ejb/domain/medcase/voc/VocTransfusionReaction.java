package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник трансфузионных реакций
 * @author azviagin
 *
 */
@Comment("Справочник трансфузионных реакций")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocTransfusionReaction extends VocBaseEntity{
	/** Описание */
	private String description;
}
