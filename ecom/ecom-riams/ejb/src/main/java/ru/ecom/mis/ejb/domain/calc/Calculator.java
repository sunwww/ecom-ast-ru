package ru.ecom.mis.ejb.domain.calc;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.protocol.parameter.voc.VocMeasureUnit;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;




/** Калькулятор человека */
@Comment("Калькулятор человека")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class Calculator extends BaseEntity{

	/** Название */
	private String name;
	
	/** Имя создателя */
	private String username;
	
	/** Единица измерения */
	@Comment("Единица измерения")
	@OneToOne
	public VocMeasureUnit getValueOfResult() {return valueOfResult;}
	private VocMeasureUnit valueOfResult;
	
	/** Комментарий */
	private String comment;


	/** Создавать дневник? */
	private Boolean createDiary;

	/** Тэг *.tag */
	private String tag;
}
