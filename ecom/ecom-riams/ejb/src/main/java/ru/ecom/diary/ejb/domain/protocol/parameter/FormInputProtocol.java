package ru.ecom.diary.ejb.domain.protocol.parameter;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class FormInputProtocol extends BaseEntity{

	/** Карта оценки */
	private Long assessmentCard;
	
	/** Параметр */
	@Comment("Параметр")
	@OneToOne
	public Parameter getParameter() {return parameter;}
	/** Параметр */
	private Parameter parameter;

	/** Позиция на форме */
	private Long position;
	
	/** Протокол */
	@Comment("Протокол")
	@OneToOne
	public Diary getDocProtocol() {return docProtocol;}

	/** Протокол */
	private Diary docProtocol;
	
	/** Значение */
	@Comment("Значение")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getValueText() {return valueText;}

	/** Значение */
	private String valueText;
	
	/** Long */
	@Comment("Long")
	@OneToOne
	public UserValue getValueVoc() {return valueVoc;}

	/** Long */
	private UserValue valueVoc;

	/** l */
	private BigDecimal valueBD;

	/** Список значений для множественного выбора */
	private String listValues;
}
