package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник результатов гистологий")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocHistologyResult extends VocBaseEntity {
	/** С патологией */
	private Boolean isWithPathology;
}
