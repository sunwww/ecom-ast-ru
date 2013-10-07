package ru.medos.ejb.persdata.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.medos.ejb.persdata.domain.ComingDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Акт уничтожения копий
	 */
	@Comment("Акт уничтожения копий")
@Entity
@Table(schema="SQLUser")
public class CopiesDestructionAct extends Act{
	@OneToMany(mappedBy="copiesDestructionAct", cascade=CascadeType.ALL)
	public List<Identifier> getIdentifiers() {
		return theIdentifiers;
	}
	public void setIdentifiers(List<Identifier> aIdentifiers) {
		theIdentifiers = aIdentifiers;
	}
	/**
	 * Входящие документы
	 */
	private List<Identifier> theIdentifiers;
}
