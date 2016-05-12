package ru.ecom.mis.ejb.domain.claim;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class WorkFunctionClaimType extends BaseEntity{
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkfunction() {return theWorkfunction;}
	public void setWorkfunction(Long aWorkfunction) {theWorkfunction = aWorkfunction;}
	/** Рабочая функция */
	private Long theWorkfunction;
	
	/** Тип заявки */
	@Comment("Тип заявки")
	public Long getClaimType() {return theClaimType;}
	public void setClaimType(Long aClaimType) {theClaimType = aClaimType;}
	/** Тип заявки */
	private Long theClaimType;

}
