package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник медицинских услуг
 * @author azviagin,stkacheva
 *
 */
@Comment("Справочник медицинских услуг")
@Entity
@Table(schema="SQLUser")
public class VocMedService extends VocBaseEntity{
	/** Полное название */
	@Comment("Полное название")
	public String getLongName() {return theLongName;}
	public void setLongName(String aLongName) {theLongName = aLongName;}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Тип услуги */
	@Comment("Тип услуги")
	public String getServiceType() {return theServiceType;}
	public void setServiceType(String aServiceType) {theServiceType = aServiceType;}

	/** Входит в омс */
	@Comment("Входит в омс")
	public Boolean getIsOmc() {return theIsOmc;}
	public void setIsOmc(Boolean aIsOmc) {theIsOmc = aIsOmc;}

	/** Входит в омс */
	private Boolean theIsOmc;
	/** Тип услуги */
	private String theServiceType;
	/** Комментарий */
	private String theComment;
	/** Полное название */
	private String theLongName;
}
