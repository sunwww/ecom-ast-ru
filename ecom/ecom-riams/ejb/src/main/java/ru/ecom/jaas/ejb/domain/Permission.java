package ru.ecom.jaas.ejb.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.jaas.ejb.domain.voc.VocObjectPermission;
import ru.ecom.jaas.ejb.domain.voc.VocPermission;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@EntityListeners(DeleteListener.class)
@AIndexes({
    @AIndex(properties= {"object","permission","dateFrom","dateTo"})
})
@Table(schema="SQLUser")
@UnDeletable
public abstract class Permission extends BaseEntity {
	/** Дата начала актульности */
	@Comment("Дата начала актульности")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Дата окончания актульности */
	@Comment("Дата окончания актульности")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}
	
	/** Разрешение */
	@Comment("Разрешение")
	@OneToOne
	public VocPermission getPermission() {return thePermission;}
	public void setPermission(VocPermission aPermission) {thePermission = aPermission;}

	/** Объект */
	@Comment("Объект")
	@OneToOne
	public VocObjectPermission getObject() {return theObject;}
	public void setObject(VocObjectPermission aObject) {theObject = aObject;}

	/** Объект Id */
	@Comment("Объект Id")
	public String getIdObject() {return theIdObject;}
	public void setIdObject(String aIdObject) {theIdObject = aIdObject;}

	/** Пользователь, который дал разрешение */
	@Comment("Пользователь, который дал разрешение")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	/** Объект инфо */
	@Comment("Объект инфо")
	@Transient
	public String getObjectInfo() {return theObject!=null?theObject.getName():"";}
	/** Разрешение инфо */
	@Comment("Разрешение инфо")
	@Transient
	public String getPermissionInfo() {return thePermission!=null?thePermission.getName():"";}

	/** Дата начала периода редактирования */
	@Comment("Дата начала периода редактирования")
	public Date getEditPeriodFrom() {return theEditPeriodFrom;}
	public void setEditPeriodFrom(Date aEditDateFrom) {theEditPeriodFrom = aEditDateFrom;}

	/** Дата окончания периода редактирования */
	@Comment("Дата окончания периода редактирования")
	public Date getEditPeriodTo() {return theEditPeriodTo;}
	public void setEditPeriodTo(Date aEditPeriodTo) {theEditPeriodTo = aEditPeriodTo;}

	/** Дата окончания периода редактирования */
	private Date theEditPeriodTo;
	/** Дата начала периода редактирования  */
	private Date theEditPeriodFrom;
	/** Время создания */
	private Time theCreateTime;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь, который дал разрешение */
	private String theCreateUsername;
	/** Объект Id */
	private String theIdObject;
	/** Объект */
	private VocObjectPermission theObject;
	/** Разрешение */
	private VocPermission thePermission;
	/** Дата окончания актульности */
	private Date theDateTo;
	/** Дата начала актульности */
	private Date theDateFrom;

	/** Удаленная запись */
	@Comment("Удаленная запись")
	public Boolean getIsDeleted() {return theIsDeleted;}
	public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
	/** Удаленная запись */
	private Boolean theIsDeleted ;
}
