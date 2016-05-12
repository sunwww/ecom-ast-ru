package ru.ecom.jaas.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class VocObjectPermission extends VocBaseEntity {
	/** Название поля, по которому идет разрешение */
	@Comment("Название поля, по которому идет разрешение")
	public String getNamePermissionDate() {return theNamePermissionDate;}
	public void setNamePermissionDate(String aNamePermissionDate) {theNamePermissionDate = aNamePermissionDate;}

	/** Таблица */
	@Comment("Таблица")
	public String getNamePermissionTable() {return theNamePermissionTable;}
	public void setNamePermissionTable(String aNamePermissionTable) {theNamePermissionTable = aNamePermissionTable;}

	/** Таблица */
	private String theNamePermissionTable;
	/** Название поля, по которому идет разрешение */
	private String theNamePermissionDate;

}
