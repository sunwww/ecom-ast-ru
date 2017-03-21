package ru.ecom.mis.ejb.form.calc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.Calculator;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = Calculator.class)
@Comment("Форма калькулятора")
@WebTrail(comment = "Форма калькулятора"
, nameProperties= "id", list="entityParentList-calculator.do", view="entityParentView-calc_calculator.do")
//@Parent(property="dispCard", parentForm=ExtDispCardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")
public class CalculatorForm extends IdEntityForm{

	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	private String theName;
	
	/** Имя создателя */
	@Comment("Имя создателя")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	private String theUsername;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;	}
	private String theComment;
}
