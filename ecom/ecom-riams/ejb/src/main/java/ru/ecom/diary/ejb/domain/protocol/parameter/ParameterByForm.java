package ru.ecom.diary.ejb.domain.protocol.parameter;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class ParameterByForm extends BaseEntity{
	
	/** Использовать значение параметра по умолчанию */
	private Boolean useDefaultValue;
	
	/** Параметр */
	@Comment("Параметр")
	@OneToOne
	public Parameter getParameter() {return parameter;}


	/** Форма ввода для шаблона */
	@Comment("Форма ввода для шаблона")
	@ManyToOne
	public FormInputProtocol getFormInput() {return formInput;}

	/** Форма ввода для шаблона */
	private FormInputProtocol formInput;
	/** Позиция на форме */
	private Long position;
	/** Параметр */
	private Parameter parameter;
	
	/** Шаблон */
	@Comment("Шаблон")
	@OneToOne
	public TemplateProtocol getTemplate() {return template;}

	/** Шаблон */
	private TemplateProtocol template;

	/** Пользователь */
	private String createUsername;
	/** Дата создания */
	private Date createDate;
	
	/** Карта оценки */
	private Long assessmentCard;
}
