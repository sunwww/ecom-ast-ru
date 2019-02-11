package ru.ecom.jaas.ejb.form;

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
public class PermissionForm extends IdEntityForm {
	/** Дата начала актульности */
	@Comment("Дата начала актульности")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания актульности */
	@Comment("Дата окончания актульности")
	@Persist @DateString @DoDateString 
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	/** Разрешение */
	@Comment("Разрешение")
	@Persist @Required
	public Long getPermission() {return thePermission;}
	public void setPermission(Long aPermission) {thePermission = aPermission;}

	/** Объект Id */
	@Comment("Объект Id")
	@Persist
	public String getIdObject() {return theIdObject;}
	public void setIdObject(String aIdObject) {theIdObject = aIdObject;}

	/** Пользователь, который дал разрешение */
	@Comment("Пользователь, который дал разрешение")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	
	/** Разрешение инфо */
	@Comment("Разрешение инфо")
	@Persist
	public String getPermissionInfo() {return thePermissionInfo;}
	public void setPermissionInfo(String aPermissionInfo) {thePermissionInfo = aPermissionInfo;}

	/** ObjectInfo */
	@Comment("ObjectInfo")
	@Persist
	public String getObjectInfo() {return theObjectInfo;}
	public void setObjectInfo(String aObjectInfo) {theObjectInfo = aObjectInfo;}

	/** Объект */
	@Comment("Объект")
	@Persist @Required
	public Long getObject() {return theObject;}
	public void setObject(Long aObject) {	theObject = aObject;}

	/** Дата начала периода редактирования */
	@Comment("Дата начала периода редактирования")
	@Persist @DateString @DoDateString @Required
	public String getEditPeriodFrom() {return theEditPeriodFrom;}
	public void setEditPeriodFrom(String aEditDateFrom) {theEditPeriodFrom = aEditDateFrom;}

	/** Дата окончания периода редактирования */
	@Comment("Дата окончания периода редактирования")
	@Persist @DateString @DoDateString @Required
	public String getEditPeriodTo() {return theEditPeriodTo;}
	public void setEditPeriodTo(String aEditPeriodTo) {theEditPeriodTo = aEditPeriodTo;}

	/** Дата окончания периода редактирования */
	private String theEditPeriodTo;
	/** Дата начала периода редактирования  */
	private String theEditPeriodFrom;
	/** Объект */
	private Long theObject;
	/** ObjectInfo */
	private String theObjectInfo;
	/** Разрешение инфо */
	private String thePermissionInfo;
	/** Время создания */
	private String theCreateTime;
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь, который дал разрешение */
	private String theCreateUsername;
	/** Объект Id */
	private String theIdObject;
	/** Разрешение */
	private Long thePermission;
	/** Дата окончания актульности */
	private String theDateTo;
	/** Дата начала актульности */
	private String theDateFrom;
}
