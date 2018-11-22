package ru.ecom.mis.ejb.form.calc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.Calculations;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = Calculations.class)
@Comment("Форма калькуляции")
@WebTrail(comment = "Форма калькуляции"
, nameProperties= "id", list="entityParentList-calculations.do", view="entityParentView-calc_calculations.do")
@Parent(property="calculator", parentForm=CalculatorForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Calc/Calculator")
public class CalculationsForm extends IdEntityForm{
	
	/** Калькулятор */
	@Comment("Калькулятор")
	@Persist
	public Long getCalculator() {return theCalculator;}
	public void setCalculator(Long aCalculator) {theCalculator = aCalculator;}
	private Long theCalculator;
	
	/** Тип */
	@Comment("Тип")
	@Persist
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}
	private Long theType;
	
	/** Порядок */
	@Comment("Порядок")
	@Persist
	public Long getOrderus() {return theOrderus;}
	public void setOrderus(Long aOrderus) {theOrderus = aOrderus;}
	private Long theOrderus;
	
	/** Значение */
	@Comment("Значение")
	@Persist
	public String getValue() {return theValue;}
	public void setValue(String aValue) {theValue = aValue;	}
	private String theValue;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}
	private String theComment;

	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getNote() {return theNote;}
	public void setNote(String aNote) {theNote = aNote;}
	private String theNote;
}
