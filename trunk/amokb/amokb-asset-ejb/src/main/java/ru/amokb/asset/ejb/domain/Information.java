package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocSecurityMark;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Информация
	 */
	@Comment("Информация")
@Entity
@Table(schema="SQLUser")
public class Information extends Asset{
	/**
	 * Место хранения
	 */
	@Comment("Место хранения")
	
	public String getStoragePath() {
		return theStoragePath;
	}
	public void setStoragePath(String aStoragePath) {
		theStoragePath = aStoragePath;
	}
	/**
	 * Место хранения
	 */
	private String theStoragePath;
	/**
	 * Метка безопасности
	 */
	@Comment("Метка безопасности")
	@OneToOne
	public VocSecurityMark getSecurityMark() {
		return theSecurityMark;
	}
	public void setSecurityMark(VocSecurityMark aSecurityMark) {
		theSecurityMark = aSecurityMark;
	}
	/**
	 * Метка безопасности
	 */
	private VocSecurityMark theSecurityMark;
}
