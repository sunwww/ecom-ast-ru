package ru.ecom.jaas.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Настройка приложения
 * @author stkacheva
 */
@Entity
@AIndexes({
	    @AIndex(unique = true, properties= {"key"})
})
@Table(schema="SQLUser")
@Setter
@Getter
public class SoftConfig extends BaseEntity {

	/** Описание */
	private String description;
	/** Значение */
	private String keyValue;
	/** Ключ */
	private String key;

	/** Невидимость в системе */
	private Boolean inVisible;
}
