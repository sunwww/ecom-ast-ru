package ru.ecom.diary.ejb.domain.protocol.parameter;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Форма ввода для протокола
 * @author stkacheva
 */
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "docProtocol" }) 
		
})
public class FormInputProtocol extends BaseEntity{

	/** Параметр */
	@Comment("Параметр")
	@OneToOne
	public Parameter getParameter() {return theParameter;}
	public void setParameter(Parameter aParameter) {theParameter = aParameter;}
	/** Параметр */
	private Parameter theParameter;
		
	/** Позиция на форме */
	@Comment("Позиция на форме")
	public Long getPosition() {return thePosition;}
	public void setPosition(Long aPosition) {thePosition = aPosition;}

	
	/** Позиция на форме */
	private Long thePosition;
	
	/** Протокол */
	@Comment("Протокол")
	@OneToOne
	public Diary getDocProtocol() {return theDocProtocol;}
	public void setDocProtocol(Diary aDocProtocol) {theDocProtocol = aDocProtocol;}

	/** Протокол */
	private Diary theDocProtocol;
	
	/** Значение */
	@Comment("Значение")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getValueText() {return theValueText;}
	public void setValueText(String aValueText) {theValueText = aValueText;}

	/** Значение */
	private String theValueText;
	
	/** Long */
	@Comment("Long")
	@OneToOne
	public UserValue getValueVoc() {return theValueVoc;}
	public void setValueVoc(UserValue aValueVoc) {theValueVoc = aValueVoc;}

	/** Long */
	private UserValue theValueVoc;
	
	/** l */
	@Comment("l")
	public BigDecimal getValueBD() {return theValueBD;}
	public void setValueBD(BigDecimal aValueBD) {theValueBD = aValueBD;}

	/** l */
	private BigDecimal theValueBD;
}
