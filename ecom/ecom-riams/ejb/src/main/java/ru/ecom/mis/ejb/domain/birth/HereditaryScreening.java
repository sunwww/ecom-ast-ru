package ru.ecom.mis.ejb.domain.birth;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *Скрининг на наследственные заболевания 
 * @author azviagin
 *
 */

@Comment("Скрининг на наследственные заболевания ")
@Entity
@Table(schema="SQLUser")
public class HereditaryScreening extends Inspection{
	@Transient
	public String getTypeInformation() {
		return  "Скрининг на наследственные заболевания" ;
	}
	@Transient
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		ret.append(getNotes()) ;
		return ret.toString() ;
	}
}
