package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Код льготы")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPrivilegeCode extends VocIdCodeName {
	/** Категория льготника */
	private Long category;
}
