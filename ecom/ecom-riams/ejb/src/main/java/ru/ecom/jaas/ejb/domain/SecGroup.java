package ru.ecom.jaas.ejb.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Setter
@Getter
public class SecGroup extends BaseEntity {

	/** Список пользователей */
	@Comment("Список пользователей")
	@ManyToMany
	public List<SecUser> getSecUsers() {return secUsers;}

	/** Комментарий */
	private String comment;
	/** Список пользователей */
	private List<SecUser> secUsers;
	/** Название группы */
	private String name;
}
