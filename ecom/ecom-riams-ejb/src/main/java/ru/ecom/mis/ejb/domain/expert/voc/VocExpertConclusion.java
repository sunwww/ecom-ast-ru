package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Справочник обоснований заключений по КЭР")
public class VocExpertConclusion extends VocBaseEntity{
	/** Код основной */
	@Comment("Код основной")
	public String getCodeAddtion() {return theCodeAddtion;}
	public void setCodeAddtion(String aCodeAddtion) {theCodeAddtion = aCodeAddtion;}
	/** Код основной */
	private String theCodeAddtion;
}
