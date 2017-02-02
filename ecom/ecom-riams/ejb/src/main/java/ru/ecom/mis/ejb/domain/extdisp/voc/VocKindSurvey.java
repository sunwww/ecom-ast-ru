package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Справочник видов обследования
 */
@Comment("Справочник видов обследования")
@Entity
@Table(schema="SQLUser")
public class VocKindSurvey extends VocBaseEntity{
	
}
