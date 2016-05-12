package ru.ecom.diary.ejb.domain.protocol.parameter.user;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
public class UserValue extends BaseEntity{
	/** Значение */
	@Comment("Значение")
	public String getName() {return theName;}
	public void setName(String aValue) {theName = aValue;}

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@ManyToOne
	public UserDomain getDomain() {return theDomain;}
	public void setDomain(UserDomain aDomain) {theDomain = aDomain;	}

	/** Пользовательский справочник */
	private UserDomain theDomain;
	/** Значение */
	private String theName;
	
	/** Кол-во баллов */
	@Comment("Кол-во баллов")
	public BigDecimal getCntBall() {return theCntBall;}
	public void setCntBall(BigDecimal aCntBall) {theCntBall = aCntBall;}

	/** Кол-во баллов */
	private BigDecimal theCntBall;

}
