package ru.ecom.jaas.ejb.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Пользователь
 */
@Entity
@AIndexes({
    @AIndex(properties= ("login"))
    ,@AIndex(properties= ("fullname"))
    ,@AIndex(properties="disable")
    ,@AIndex(properties="isRemoteUser")
})
@Table(schema="SQLUser")
@Setter
@Getter
public class SecUser extends BaseEntity {

	/** Сменить пароль при входе в систему */
	private Boolean changePasswordAtLogin;

	/** Дата последнего изменение пароля */
	private Date passwordChangedDate;

    /** Роли пользователя */
    @ManyToMany
    public List<SecRole> getRoles() { return roles ; }

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
	/** Удаленный пользователь */
	private Boolean isRemoteUser;
	/** Закеширован */
	private Boolean isHash;
	/** Отключен */
	private Boolean disable;
/** Роли пользователя */
    private List<SecRole> roles ;
    /** Отключен */
    private boolean disabled ;
    /** Комментарий */
    private String comment ;
    /** Полное имя пользователя */
    private String fullname ;
    /** Наименование пользователя */
    private String login ;
    /** Пароль */
    private String password ;
    /** Можно копировать роли в инфекционное отделение*/
	private Boolean enabledForCopy ;
}
