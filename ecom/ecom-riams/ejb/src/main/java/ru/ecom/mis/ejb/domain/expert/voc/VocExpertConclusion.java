package ru.ecom.mis.ejb.domain.expert.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Справочник обоснований заключений по КЭР")
@Getter
@Setter
public class VocExpertConclusion extends VocBaseEntity{
	/** Код основной */
	private String codeAddtion;
	/** Неактуальный */
	private Boolean noActuality;
}
