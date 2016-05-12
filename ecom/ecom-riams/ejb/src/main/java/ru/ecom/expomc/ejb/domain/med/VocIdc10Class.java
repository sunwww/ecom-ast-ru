package ru.ecom.expomc.ejb.domain.med;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Класс МКБ 10")
@Table(schema="SQLUser")
public class VocIdc10Class extends VocBaseEntity {
	/** Коды МКБ */
	@Comment("Коды МКБ")
	@OneToMany(mappedBy="idcClass", cascade=CascadeType.ALL)
	public List<VocIdc10> getIdcs() {
		return theIdcs;
	}

	public void setIdcs(List<VocIdc10> aIdcs) {
		theIdcs = aIdcs;
	}

	/** Коды МКБ */
	private List<VocIdc10> theIdcs;
}
