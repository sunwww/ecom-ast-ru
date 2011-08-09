package ru.ecom.diary.ejb.domain.protocol.parameter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Параметр формы
 * @author stkacheva
 */
@Entity
@Table(schema="SQLUser")
public class ParameterByForm extends BaseEntity{
	/** Параметр */
	@Comment("Параметр")
	@OneToOne
	public Parameter getParameter() {return theParameter;}
	public void setParameter(Parameter aParameter) {theParameter = aParameter;}
	
	/** Позиция на форме */
	@Comment("Позиция на форме")
	public Long getPosition() {return thePosition;}
	public void setPosition(Long aPosition) {thePosition = aPosition;}

	/** Форма ввода для шаблона */
	@Comment("Форма ввода для шаблона")
	@ManyToOne
	public FormInputProtocol getFormInput() {return theFormInput;}
	public void setFormInput(FormInputProtocol aFormInput) {theFormInput = aFormInput;}

	/** Форма ввода для шаблона */
	private FormInputProtocol theFormInput;
	/** Позиция на форме */
	private Long thePosition;
	/** Параметр */
	private Parameter theParameter;
}
