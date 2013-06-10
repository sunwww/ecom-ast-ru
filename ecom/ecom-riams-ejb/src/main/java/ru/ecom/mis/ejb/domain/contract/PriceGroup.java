package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"name"})
	,@AIndex(unique= false, properties = {"priceList"})
	,@AIndex(unique= false, properties = {"parent"})
})
public class PriceGroup extends BaseEntity {
	/** Прейскурант */
	@Comment("Прейскурант")
	@OneToOne
	public PriceList getPriceList() {return thePriceList;}
	public void setPriceList(PriceList aPriceList) {thePriceList = aPriceList;}

	/** Наименование */
	@Comment("Наименование")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Код */
	@Comment("Код")
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код для экспорта */
	@Comment("Код для экспорта")
	public String getExpCode() {return theExpCode;}
	public void setExpCode(String aExpCode) {theExpCode = aExpCode;}

	/** Родительская группа */
	@Comment("Родительская группа")
	@OneToOne
	public PriceGroup getParent() {return theParent;}
	public void setParent(PriceGroup aParent) {theParent = aParent;}

	/** Комментарий */
	@Comment("Комментарий")
	@Column(length=2500)
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Родитель для экспорта */
	@Comment("Родитель для экспорта")
	public String getExpParent() {return theExpParent;}
	public void setExpParent(String aExpParent) {theExpParent = aExpParent;}

	/** Родитель для экспорта */
	private String theExpParent;
	/** Комментарий */
	private String theComment;
	/** Родительская группа */
	private PriceGroup theParent;
	/** Код для экспорта */
	private String theExpCode;
	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
	/** Прейскурант */
	private PriceList thePriceList;

}
