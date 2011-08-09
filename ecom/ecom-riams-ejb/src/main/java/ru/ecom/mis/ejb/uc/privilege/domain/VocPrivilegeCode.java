package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Код льготы")
@Table(schema="SQLUser")
public class VocPrivilegeCode extends VocIdCodeName {
	/** Категория льготника */
	@Comment("Категория льготника")
	public Long getCategory() {
		return theCategory;
	}

	public void setCategory(Long aCategory) {
		theCategory = aCategory;
	}

	/** Категория льготника */
	private Long theCategory;
}
