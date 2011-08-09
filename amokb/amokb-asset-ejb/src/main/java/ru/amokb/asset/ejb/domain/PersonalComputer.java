package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Персональный компьютер
	 */
	@Comment("Персональный компьютер")
@Entity
@Table(schema="SQLUser")
public class PersonalComputer extends Computer{
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
