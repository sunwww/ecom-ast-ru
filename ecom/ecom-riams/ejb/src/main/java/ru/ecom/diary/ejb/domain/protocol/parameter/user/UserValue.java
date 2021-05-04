package ru.ecom.diary.ejb.domain.protocol.parameter.user;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Значение из пользовательского справочника 
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "domain","name" }) })
@Getter
@Setter
public class UserValue extends BaseEntity{
	/** Использовать значение по умолчанию */
	private Boolean useByDefault;

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@ManyToOne
	public UserDomain getDomain() {return domain;}

	/** Пользовательский справочник */
	private UserDomain domain;
	/** Значение */
	private String name;
	
	/** Кол-во баллов */
	private BigDecimal cntBall;

	/** Сообщение при выборе значения */
	private String comment;

}
