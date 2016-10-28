package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Метод операции: эндоскопический, открытый")
public class VocOperationMethod extends VocBaseEntity {

	/** С использованием эндоскопии */
	@Comment("С использованием эндоскопии")
	public Boolean getEndoscopyUse() {return theEndoscopyUse;}
	public void setEndoscopyUse(Boolean aEndoscopyUse) {theEndoscopyUse = aEndoscopyUse;}
	/** С использованием эндоскопии */
	private Boolean theEndoscopyUse;
}
