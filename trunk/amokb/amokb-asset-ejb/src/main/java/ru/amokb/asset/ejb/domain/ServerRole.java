package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocServerRole;
import ru.ecom.ejb.domain.simple.BaseEntity;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Роль сервера
	 */
	@Comment("Роль сервера")
@Entity
@Table(schema="SQLUser")
public class ServerRole extends BaseEntity{
	/**
	 * Сервер
	 */
	@Comment("Сервер")
	@ManyToOne
	public Server getServer() {
		return theServer;
	}
	public void setServer(Server aServer) {
		theServer = aServer;
	}
	/**
	 * Сервер
	 */
	private Server theServer;
	/**
	 * Тип роли
	 */
	@Comment("Тип роли")
	@OneToOne
	public VocServerRole getRoleType() {
		return theRoleType;
	}
	public void setRoleType(VocServerRole aRoleType) {
		theRoleType = aRoleType;
	}
	/**
	 * Тип роли
	 */
	private VocServerRole theRoleType;
}
