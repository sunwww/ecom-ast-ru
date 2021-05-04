package ru.ecom.jaas.ejb.domain;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Роль
 */
@Entity
@AIndexes({
	@AIndex(properties={"key"})
})
@Table(schema="SQLUser")
@Setter
@Getter
public class SecRole extends BaseEntity {

    /** Политики безопасности */
    @ManyToMany(cascade = ALL)
    public Collection<SecPolicy> getSecPolicies() { return secPolicies ; }
    public void setSecPolicies(Collection<SecPolicy> aSecPolicies) { secPolicies = aSecPolicies ; }

	/** Зависимые роли */
	@Comment("Зависимые роли")
	@ManyToMany
	public List<SecRole> getChildren() {return children;}

	/** Системный? */
	@Comment("Системный?")
	public Boolean getIsSystems() {return isSystems;}

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
	/** Системный? */
	private Boolean isSystems;
	/** Зависимые роли */
	private List<SecRole> children;

	/** Ключ */
	private String key;
    /** Политики безопасности */
    private Collection<SecPolicy> secPolicies ;
    /** Комментарий */
    private String comment ;
    /** Название */
    private String name ;

}
