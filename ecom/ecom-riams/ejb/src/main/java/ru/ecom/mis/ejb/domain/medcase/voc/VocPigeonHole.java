package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник приемных отделений")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPigeonHole extends VocBaseEntity{
	/** Разделяются номера стат.карт на экстренные плановые */
	private Boolean isStatStubEmerPlan;
	/** Префикс стат.карт после */
	private String prefixAfter;
	/** Префикс стат.карт до */
	private String prefixBefore;

}
