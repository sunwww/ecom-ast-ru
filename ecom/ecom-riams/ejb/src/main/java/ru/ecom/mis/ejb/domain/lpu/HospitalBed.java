package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class HospitalBed extends HospitalRoom {
	/** Дополнительные */
	private Boolean isAddition;

}
