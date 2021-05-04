package ru.ecom.mis.ejb.domain.assessmentcard;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import java.sql.Date;

@Entity
@Getter
@Setter
public class AssessmentCardTemplate extends VocBaseEntity{
	
	/** Группировать параметры по группам */
	private Boolean isGroupParameters;	
	/** Пользователь создавший запись */
	private String createUsername;
	/** Дата создания */
	private Date createDate;
	/** В архиве */
	private Boolean isArchive;

}
