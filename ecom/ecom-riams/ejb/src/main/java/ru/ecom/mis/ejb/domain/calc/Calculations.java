package ru.ecom.mis.ejb.domain.calc;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Калькуляции */
@Comment("Калькуляции")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class Calculations extends BaseEntity{

	/** Калькулятор */
	@Comment("Калькулятор")
	@OneToOne
	public Calculator getCalculator() {return calculator;}
	private Calculator calculator;
	
	/** Тип */
	@Comment("Тип")
	@OneToOne
	public VocTypeCalc getType() {return type;}
	private VocTypeCalc type;

	
	/** Порядок */
	private Long orderus;
	
	/** Значение */
	private String value;
	
	/** Комментарий */
	private String comment;

    /** Примечание */
    private String note;
}