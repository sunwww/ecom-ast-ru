package ru.amokb.asset.ejb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Сервер
	 */
	@Comment("Сервер")
@Entity
@Table(schema="SQLUser")
public class Server extends Computer{
	@OneToMany(mappedBy="server", cascade=CascadeType.ALL)
	public List<ServerRole> getServerRoles() {
		return theServerRoles;
	}
	public void setServerRoles(List<ServerRole> aServerRoles) {
		theServerRoles = aServerRoles;
	}
	/**
	 * Серверные роли
	 */
	private List<ServerRole> theServerRoles;
	/**
	 * Пароль локального администратора
	 */
	@Comment("Пароль локального администратора")
	
	public Boolean getLocalAdminPassword() {
		return theLocalAdminPassword;
	}
	public void setLocalAdminPassword(Boolean aLocalAdminPassword) {
		theLocalAdminPassword = aLocalAdminPassword;
	}
	/**
	 * Пароль локального администратора
	 */
	private Boolean theLocalAdminPassword;
}
