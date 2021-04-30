package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Метод операции: эндоскопический, открытый")
@Getter
@Setter
public class VocOperationMethod extends VocBaseEntity {
	/** С использованием эндоскопии */
	private Boolean endoscopyUse;
}
