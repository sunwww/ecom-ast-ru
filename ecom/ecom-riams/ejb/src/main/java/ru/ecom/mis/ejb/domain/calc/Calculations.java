package ru.ecom.mis.ejb.domain.calc;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Калькуляции */
@Comment("Калькуляции")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class Calculations extends BaseEntity{

	/** Калькулятор */
	@Comment("Калькулятор")
	@OneToOne
	public Calculator getCalculator() {return theCalculator;}
	public void setCalculator(Calculator aCalculator) {theCalculator = aCalculator;	}
	private Calculator theCalculator;
	
	/** Тип */
	@Comment("Тип")
	@OneToOne
	public VocTypeCalc getType() {return theType;}
	public void setType(VocTypeCalc aType) {theType = aType;}
	private VocTypeCalc theType;

	
	/** Порядок */
	@Comment("Порядок")
	public Integer getOrderus() {return theOrderus;}
	public void setOrderus(Integer aOrderus) {theOrderus = aOrderus;}
	private Integer theOrderus;
	
	/** Значение */
	@Comment("Значение")
	public String getValue() {return theValue;}
	public void setValue(String aValue) {theValue = aValue;	}
	private String theValue;
	
	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	private String theComment;
	
}
