package ru.ecom.jaas.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocObjectPermission extends VocBaseEntity {
	/** Таблица */
	private String namePermissionTable;
	/** Название поля, по которому идет разрешение */
	private String namePermissionDate;

}
