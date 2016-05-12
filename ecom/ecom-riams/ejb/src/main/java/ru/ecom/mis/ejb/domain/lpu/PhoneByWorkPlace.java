package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocPhoneType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(properties={"workPlace"})
})
@Table(schema="SQLUser")
public class PhoneByWorkPlace extends BaseEntity {
	/** Рабочее место */
	@Comment("Рабочее место")
	@OneToOne
	public WorkPlace getWorkPlace() {return theWorkPlace;}
	public void setWorkPlace(WorkPlace aWorkPlace) {theWorkPlace = aWorkPlace;}
	
	/** Рабочее место */
	private WorkPlace theWorkPlace;
	/**
	 * Тип телефона
	 */
	@Comment("Тип телефона")
	@OneToOne
	public VocPhoneType getPhoneType() {return thePhoneType;}
	public void setPhoneType(VocPhoneType aPhoneType) {thePhoneType = aPhoneType;}
	/**
	 * Тип телефона
	 */
	private VocPhoneType thePhoneType;
	/**
	 * Номер телефона
	 */
	@Comment("Номер телефона")
	public String getPhoneNumber() {return thePhoneNumber;}
	public void setPhoneNumber(String aPhoneNumber) {thePhoneNumber = aPhoneNumber;}
	/**
	 * Номер телефона
	 */
	private String thePhoneNumber;
	/**
	 * Основной телефон
	 */
	@Comment("Основной телефон")
	public Boolean getIsPrimary() {return theIsPrimary;}
	public void setIsPrimary(Boolean aIsPrimary) {theIsPrimary = aIsPrimary;}
	/**
	 * Основной телефон
	 */
	private Boolean theIsPrimary;
	/**
	 * Комментарий
	 */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	/**
	 * Комментарий
	 */
	private String theComment;	
}
