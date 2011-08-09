package ru.ecom.diary.ejb.domain.protocol.parameter.user;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Значение из пользовательского справочника 
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
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

}
