package ru.ecom.jaas.ejb.form;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

import javax.persistence.Id;

/**
 * Пользователь
 */
@EntityForm
@EntityFormPersistance(clazz= SecUser.class)
@Comment("Пользователь")
@EntityFormSecurityPrefix("/Policy/Jaas/SecUser")
@Setter
public class SecUserForm  extends IdEntityForm {

	/** Сменить пароль при входе в систему */
	@Comment("Сменить пароль при входе в систему")
	@Persist
	public Boolean getChangePasswordAtLogin() {return changePasswordAtLogin;}
	/** Сменить пароль при входе в систему */
	private Boolean changePasswordAtLogin;
	
	/** Дата изменение пароля */
	@Comment("Дата изменение пароля")
	@Persist
	public String getPasswordChangedDate() {return passwordChangedDate;}
	/** Дата изменение пароля */
	private String passwordChangedDate;
	
	/** Идентификатор */
    @Id
    public long getId() { return id ; }

    /** Пароль */
    @Required
    @Persist
    public String getPassword() { return password ; }

    /** Логин */
    @Required
    @Persist
    public String getLogin() { return login ; }

    /** Полное имя */
    @Required
    @Persist
    public String getFullname() { return fullname ; }

    /** Комментарий */
    @Persist
    public String getComment() { return comment ; }

    /** Отключен */
	@Comment("Отключен")
	@Persist
	public Boolean getDisable() {return disable;}

	/** Закеширован */
	@Comment("Закеширован")
	@Persist
	public Boolean getIsHash() {return isHash;}

	/** Удаленный пользователь */
	@Comment("Удаленный пользователь")
	@Persist
	public Boolean getIsRemoteUser() {return isRemoteUser;}

	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return workFunction;}

	/** Системный? */
	@Comment("Системный?")
	@Persist
	public Boolean getIsSystems() {return isSystems;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь */
	@Comment("Пользователь")
	public Long getUserCopy() {return userCopy;}

	/** Можно копировать роли в инфекционное отделение */
	@Comment("Можно копировать роли в инфекционное отделение")
	public Boolean getEnabledForCopy() {return enabledForCopy;}

	/** Пользователь */
	private Long userCopy;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	/** Системный? */
	private Boolean isSystems;
	/** Рабочая функция */
	private Long workFunction;
	/** Удаленный пользователь */
	private Boolean isRemoteUser;
	/** Закеширован */
	private Boolean isHash;
	/** Отключен */
	private Boolean disable;
    /** Логин */
    private String login ;
    /** Комментарий */
    private String comment ;
    /** Идентификатор */
    private long id ;
    /** Полное имя */
    private String fullname ;
    /** Пароль */
    private String password ;
	/** Можно копировать роли в инфекционное отделение*/
	private Boolean enabledForCopy ;
}
