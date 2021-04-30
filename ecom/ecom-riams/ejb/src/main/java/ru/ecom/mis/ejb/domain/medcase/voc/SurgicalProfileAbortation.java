package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Связь профиля операции с типом аборта")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class SurgicalProfileAbortation extends BaseEntity{
	/** Тип профиля */
	private Long surgicalProfile;
	
	/** Тип аборта */
	private Long abortation;

}
