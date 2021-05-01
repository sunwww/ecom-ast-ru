package ru.ecom.jaas.ejb.form;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.jaas.ejb.domain.Permission;
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

@EntityForm
@EntityFormPersistance(clazz= Permission.class)
@Comment("Разрешения")
@Subclasses(value = { UserPermissionForm.class })
@EntityFormSecurityPrefix("/Policy/Jaas/Permission")
@WebTrail(comment="Разрешения", nameProperties="name", view="entityView-sec_permission.do")
@Setter
public class PermissionForm extends IdEntityForm {
	/** Дата начала актульности */
	@Comment("Дата начала актульности")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return dateFrom;}

	/** Дата окончания актульности */
	@Comment("Дата окончания актульности")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return dateTo;}

	/** Разрешение */
	@Comment("Разрешение")
	@Persist @Required
	public Long getPermission() {return permission;}

	/** Объект Id */
	@Comment("Объект Id")
	@Persist
	public String getIdObject() {return idObject;}

	/** Пользователь, который дал разрешение */
	@Comment("Пользователь, который дал разрешение")
	@Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return createDate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getCreateTime() {return createTime;}

	/** Разрешение инфо */
	@Comment("Разрешение инфо")
	@Persist
	public String getPermissionInfo() {return permissionInfo;}

	/** ObjectInfo */
	@Comment("ObjectInfo")
	@Persist
	public String getObjectInfo() {return objectInfo;}

	/** Объект */
	@Comment("Объект")
	@Persist @Required
	public Long getObject() {return object;}

	/** Дата начала периода редактирования */
	@Comment("Дата начала периода редактирования")
	@Persist @DateString @DoDateString @Required
	public String getEditPeriodFrom() {return editPeriodFrom;}

	/** Дата окончания периода редактирования */
	@Comment("Дата окончания периода редактирования")
	@Persist @DateString @DoDateString @Required
	public String getEditPeriodTo() {return editPeriodTo;}

	/** Дата окончания периода редактирования */
	private String editPeriodTo;
	/** Дата начала периода редактирования  */
	private String editPeriodFrom;
	/** Объект */
	private Long object;
	/** ObjectInfo */
	private String objectInfo;
	/** Разрешение инфо */
	private String permissionInfo;
	/** Время создания */
	private String createTime;
	/** Дата создания */
	private String createDate;
	/** Пользователь, который дал разрешение */
	private String createUsername;
	/** Объект Id */
	private String idObject;
	/** Разрешение */
	private Long permission;
	/** Дата окончания актульности */
	private String dateTo;
	/** Дата начала актульности */
	private String dateFrom;
}
