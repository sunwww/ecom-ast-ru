package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@MappedSuperclass
@Getter
@Setter
public class VocBall extends VocBaseEntity{
	/** Балл */
	private Integer ball;

}
