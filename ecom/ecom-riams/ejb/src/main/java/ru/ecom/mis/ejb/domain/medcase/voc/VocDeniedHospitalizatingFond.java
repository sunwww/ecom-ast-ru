package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Стационар. Справочник отказов при госпитализации по фонду.")
@Table(schema="SQLUser")
public class VocDeniedHospitalizatingFond extends VocBaseEntity{
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарий */
	private String theComment;
}
