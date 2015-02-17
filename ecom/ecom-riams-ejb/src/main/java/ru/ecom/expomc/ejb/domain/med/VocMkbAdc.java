package ru.ecom.expomc.ejb.domain.med;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Дополнительный код МКБ")
@Table(schema="SQLUser")
public class VocMkbAdc extends VocBaseEntity {
	/** Код МКБ */
	@Comment("Код МКБ")
	public String getMkb() {return theMkb;}
	public void setMkb(String aMkb) {theMkb = aMkb;}

	/** Код МКБ */
	private String theMkb;
}
