package ru.ecom.mis.ejb.domain.birth;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 *Скрининг на наследственные заболевания 
 * @author azviagin
 *
 */

@Comment("Скрининг на наследственные заболевания ")
@Entity
public class HereditaryScreening extends Inspection {
	@Transient
	public String getTypeInformation() {
		return  "Скрининг на наследственные заболевания" ;
	}
	@Transient
	public String getInformation() {
		return getNotes();
	}
}
