package ru.ecom.mis.ejb.domain.claim;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class WorkFunctionClaimType extends BaseEntity{
	
	/** Рабочая функция */
	private Long workfunction;
	
	/** Тип заявки */
	private Long claimType;

}
