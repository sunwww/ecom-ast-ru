package ru.ecom.mis.ejb.form.calc;

import lombok.Setter;
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
@Setter
public class CalculationsForm extends IdEntityForm{
	
	/** Калькулятор */
	@Comment("Калькулятор")
	@Persist
	public Long getCalculator() {return calculator;}
	private Long calculator;
	
	/** Тип */
	@Comment("Тип")
	@Persist
	public Long getType() {return type;}
	private Long type;
	
	/** Порядок */
	@Comment("Порядок")
	@Persist
	public Long getOrderus() {return orderus;}
	private Long orderus;
	
	/** Значение */
	@Comment("Значение")
	@Persist
	public String getValue() {return value;}
	private String value;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return comment;}
	private String comment;

	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getNote() {return note;}
	private String note;
}
