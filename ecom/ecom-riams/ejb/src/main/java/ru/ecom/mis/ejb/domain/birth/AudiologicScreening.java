package ru.ecom.mis.ejb.domain.birth;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Аудиологический скрининг
 * @author azviagin
 *
 */

@Comment("Аудиологический скрининг")
@Entity
@Table(schema="SQLUser")
public class AudiologicScreening extends Inspection{
	@Transient
	public String getTypeInformation() {
		return  "Аудиологический скрининг" ;
	}
	@Transient
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		ret.append(getNotes()) ;
		return ret.toString() ;
	}
}
