package ru.ecom.diary.ejb.domain.protocol.parameter.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Пользовательский справочник
 * @author stkacheva
 *
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class UserDomain extends BaseEntity{
	
	/** Код */
	private String code;
	

	/** Список значений */
	@Comment("Список значений")
	@OneToMany(mappedBy="domain")
	public List<UserValue> getValues() {return values;}

	/** Список значений */
	private List<UserValue> values;
	/** Название */
	private String name;
}
