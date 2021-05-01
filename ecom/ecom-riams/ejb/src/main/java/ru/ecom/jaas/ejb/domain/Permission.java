package ru.ecom.jaas.ejb.domain;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public abstract class Permission extends BaseEntity {

	/** Разрешение */
	@Comment("Разрешение")
	@OneToOne
	public VocPermission getPermission() {return permission;}

	/** Объект */
	@Comment("Объект")
	@OneToOne
	public VocObjectPermission getObject() {return object;}

	/** Объект инфо */
	@Comment("Объект инфо")
	@Transient
	public String getObjectInfo() {return object!=null?object.getName():"";}
	/** Разрешение инфо */
	@Comment("Разрешение инфо")
	@Transient
	public String getPermissionInfo() {return permission!=null?permission.getName():"";}


	/** Дата окончания периода редактирования */
	private Date editPeriodTo;
	/** Дата начала периода редактирования  */
	private Date editPeriodFrom;
	/** Время создания */
	private Time createTime;
	/** Дата создания */
	private Date createDate;
	/** Пользователь, который дал разрешение */
	private String createUsername;
	/** Объект Id */
	private String idObject;
	/** Объект */
	private VocObjectPermission object;
	/** Разрешение */
	private VocPermission permission;
	/** Дата окончания актульности */
	private Date dateTo;
	/** Дата начала актульности */
	private Date dateFrom;

	/** Удаленная запись */
	private Boolean isDeleted ;
}
