package ru.ecom.mis.ejb.domain.worker.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Группа наград
 * @author oegorova
 *
 */
@Comment("Группа наград")
@Entity
@Table(schema="SQLUser")
public class VocAwardGroup extends VocBaseEntity{
	
	/** Типы наград */
	@Comment("Типы наград")
	@OneToMany(mappedBy="group", cascade=CascadeType.ALL)
	public List<VocAwardType> getVocAwardTypes() {
		return theVocAwardTypes;
	}

	public void setVocAwardTypes(List<VocAwardType> aVocAwardTypes) {
		theVocAwardTypes = aVocAwardTypes;
	}

	/** Рабочие функции */
	private List<VocAwardType> theVocAwardTypes;

}
