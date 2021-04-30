package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник бригад скорой помощи")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocAmbulance extends VocBaseEntity {
	/** Профиль */
	private String omcDepType;
}
