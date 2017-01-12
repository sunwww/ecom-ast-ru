package ru.ecom.diary.ejb.domain.protocol.parameter;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Параметр формы
 * @author stkacheva
 */
@Entity
@Table(schema="SQLUser")
@AIndexes({
    @AIndex(properties="template")
    }) 
@EntityListeners(DeleteListener.class)
public class ParameterByForm extends BaseEntity{
	
	/** Использовать значение параметра по умолчанию */
	@Comment("Использовать значение параметра по умолчанию")
	public Boolean getUseDefaultValue() {return theUseDefaultValue;}
	public void setUseDefaultValue(Boolean aUseDefaultValue) {theUseDefaultValue = aUseDefaultValue;}
	/** Использовать значение параметра по умолчанию */
	private Boolean theUseDefaultValue;
	
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
	
	/** Шаблон */
	@Comment("Шаблон")
	@OneToOne
	public TemplateProtocol getTemplate() {return theTemplate;}
	public void setTemplate(TemplateProtocol aTemplate) {theTemplate = aTemplate;}

	/** Шаблон */
	private TemplateProtocol theTemplate;
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Пользователь */
	@Comment("Пользователь")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Пользователь */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Карта оценки */
	@Comment("Карта оценки")
	public Long getAssessmentCard() {return theAssessmentCard;}
	public void setAssessmentCard(Long aAssessmentCard) {theAssessmentCard = aAssessmentCard;}
	/** Карта оценки */
	private Long theAssessmentCard;
}
