package ru.ecom.jaas.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Настройка приложения
 * @author stkacheva
 */
@Entity
@AIndexes({
	    @AIndex(unique = true, properties= {"key"})
	    	    //,@AIndex(unique = true, properties= {"keyValue"})
})
@Table(schema="SQLUser")
public class SoftConfig extends BaseEntity {
	/** Ключ */
	@Comment("Ключ")
	public String getKey() {return theKey;}
	public void setKey(String aKey) {theKey = aKey;}

	/** Значение */
	@Comment("Значение")
	public String getKeyValue() {return theKeyValue;}
	public void setKeyValue(String aValue) {theKeyValue = aValue;}

	/** Описание */
	@Comment("Описание")
	public String getDescription() {return theDescription;}
	public void setDescription(String aDescription) {theDescription = aDescription;}

	/** Описание */
	private String theDescription;
	/** Значение */
	private String theKeyValue;
	/** Ключ */
	private String theKey;

	/** Невидимость в системе */
	@Comment("Невидимость в системе")
	public Boolean getInVisible() {return theInVisible;}
	public void setInVisible(Boolean aInVisible) {theInVisible = aInVisible;}
	/** Невидимость в системе */
	private Boolean theInVisible;
}
