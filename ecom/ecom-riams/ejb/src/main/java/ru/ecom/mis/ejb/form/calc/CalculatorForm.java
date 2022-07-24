package ru.ecom.mis.ejb.form.calc;

import lombok.Setter;
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
, nameProperties= "id", list="entityList-calc_calculator.do", view="entityView-calc_calculator.do")
@EntityFormSecurityPrefix("/Policy/Mis/Calc/Calculator")
@Setter
public class CalculatorForm extends IdEntityForm{

	/** Название */
	@Comment("Название")
	@Persist
	public String getName() {return name;}
	private String name;
	
	/** Имя создателя */
	@Comment("Имя создателя")
	@Persist
	public String getUsername() {return username;}
	private String username;
	
	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return comment;}
	private String comment;
	
	/** Единица измерения результата */
	@Comment("Единица измерения результата")
	@Persist
	public Long getValueOfResult() {return valueOfResult;}
	private Long valueOfResult;

	/** Создавать дневник? */
	@Comment("Создавать дневник?")
	@Persist
	public Boolean getCreateDiary() {return createDiary;}
	private Boolean createDiary;

	/** Тэг *.tag */
	@Comment("Тэг *.tag ")
	@Persist
	public String getTag() {return tag;}
	private String tag;

	/** Устарел? */
	@Comment("Устарел?")
	@Persist
	public Boolean getNoActual() {return noActual;}
	private Boolean noActual;

	/** ВТЭО? */
	@Comment("ВТЭО?")
	@Persist
	public Boolean getVteo() {return vteo;}
	private Boolean vteo;
}
