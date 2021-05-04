package ru.ecom.jaas.ejb.form;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.jaas.ejb.domain.SecRole;
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

/**
 * Роль
 */
@EntityForm
@EntityFormPersistance(clazz= SecRole.class)
@Comment("Роль")
@EntityFormSecurityPrefix("/Policy/Jaas/SecRole")
@Setter
public class SecRoleForm  extends IdEntityForm {
    /** Идентификатор */
    @Id
    public long getId() { return id ; }

    /** Название */
    @Persist
    @Required
    public String getName() { return name ; }

    /** Комментарий */
    @Persist
    public String getComment() { return comment ; }

    /** Ключ */
	@Comment("Ключ")
	@Persist @Required 
	public String getKey() {return key;}

	/** Дочерние роли */
	@Comment("Дочерние роли")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=SecRole.class)
	public String getChildren() {return children;}
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
	/** Дочерние роли */
	private String children;
	
	/** Ключ */
	private String key;
    /** Комментарий */
    private String comment ;
    /** Название */
    private String name ;
    /** Идентификатор */
    private long id ;
}
