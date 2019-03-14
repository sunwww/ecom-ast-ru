package ru.ecom.mis.ejb.domain.birth;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
        return getNotes();
	}
}
