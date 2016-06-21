package ru.ecom.diary.ejb.domain.protocol.parameter.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Пользовательский справочник
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
public class UserDomain extends BaseEntity{
	
	/** Код */
	@Comment("Код")
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}
	/** Код */
	private String theCode;
	
	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Список значений */
	@Comment("Список значений")
	@OneToMany(mappedBy="domain")
	public List<UserValue> getValues() {return theValues;}
	public void setValues(List<UserValue> aValues) {theValues = aValues;}

	/** Список значений */
	private List<UserValue> theValues;
	/** Название */
	private String theName;
}
