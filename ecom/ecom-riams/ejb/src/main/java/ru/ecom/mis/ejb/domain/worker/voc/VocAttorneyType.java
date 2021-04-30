package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Getter
@Setter
public class VocAttorneyType extends VocBaseEntity{
	/** Название в родительном падеже */
	private String altName;
	
	/** Не используется */
	private Boolean isArchival;
}
