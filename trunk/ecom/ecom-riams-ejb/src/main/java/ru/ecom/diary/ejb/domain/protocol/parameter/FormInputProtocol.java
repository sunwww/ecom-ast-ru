package ru.ecom.diary.ejb.domain.protocol.parameter;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Форма ввода для протокола
 * @author stkacheva
 */
@Entity
@Table(schema="SQLUser")
public class FormInputProtocol extends BaseEntity{
	/** Шаблон протокола */
	@Comment("Шаблон протокола")
	@OneToOne
	public TemplateProtocol getProtocol() {return theProtocol;}
	public void setProtocol(TemplateProtocol aProtocol) {theProtocol = aProtocol;}

	/** Список параметров формы */
	@Comment("Список параметров формы")
	@OneToMany(mappedBy="formInput", cascade=CascadeType.ALL)
	public List<ParameterByForm> getParameters() {return theParameters;	}
	public void setParameters(List<ParameterByForm> aParameters) {theParameters = aParameters;}
	
	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Название */
	private String theName;
	/** Список параметров формы */
	private List<ParameterByForm> theParameters;
	/** Шаблон протокола */
	private TemplateProtocol theProtocol;

}
