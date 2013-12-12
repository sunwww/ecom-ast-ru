package ru.ecom.mis.ejb.domain.lpu;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(properties={"parent"})
	,@AIndex(properties={"name"})
	,@AIndex(properties={"lpu"})
})
@Table(schema="SQLUser")
public class WorkPlace extends BaseEntity{
	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

	/** Родитель */
	@Comment("Родитель")
	@OneToOne
	public WorkPlace getParent() {return theParent;}
	public void setParent(WorkPlace aParent) {theParent = aParent;}


	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Не актуален */
	@Comment("Неактуален")
	public Boolean getIsNoActuality() {return theIsNoActuality;}
	public void setIsNoActuality(Boolean aIsNoActuality) {theIsNoActuality = aIsNoActuality;}

	/** Неактуален */
	private Boolean theIsNoActuality;
	/** Комментарий */
	private String theComment;
	/** Родитель */
	private WorkPlace theParent;
	/** Лечебное учреждение */
	private MisLpu theLpu;
	/** Название */
	private String theName;
	
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
}
