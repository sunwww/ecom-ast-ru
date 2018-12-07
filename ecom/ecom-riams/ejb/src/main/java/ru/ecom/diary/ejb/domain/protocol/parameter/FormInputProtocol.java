package ru.ecom.diary.ejb.domain.protocol.parameter;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
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

	/** Карта оценки */
	@Comment("Карта оценки")
	public Long getAssessmentCard() {return theAssessmentCard;}
	public void setAssessmentCard(Long aAssessmentCard) {theAssessmentCard = aAssessmentCard;}
	/** Карта оценки */
	private Long theAssessmentCard;
	
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
	
	/** Список значений для множественного выбора */
	@Comment("Список значений для множественного выбора")
	public String getListValues() {
		return theListValues;
	}

	public void setListValues(String aListValues) {
		theListValues = aListValues;
	}

	/** Список значений для множественного выбора */
	private String theListValues;
}
