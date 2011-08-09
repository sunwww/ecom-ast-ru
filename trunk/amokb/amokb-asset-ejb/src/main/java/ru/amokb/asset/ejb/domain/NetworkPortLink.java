package ru.amokb.asset.ejb.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Соединение с сетевым портом
	 */
	@Comment("Соединение с сетевым портом")
@Entity
@Table(schema="SQLUser")
public class NetworkPortLink extends BaseEntity{
	/**
	 * 1-й порт
	 */
	@Comment("1-й порт")
	@OneToOne
	public NetworkPort getPort1() {
		return thePort1;
	}
	public void setPort1(NetworkPort aPort1) {
		thePort1 = aPort1;
	}
	/**
	 * 1-й порт
	 */
	private NetworkPort thePort1;
	/**
	 * 2-й порт
	 */
	@Comment("2-й порт")
	@OneToOne
	public NetworkPort getPort2() {
		return thePort2;
	}
	public void setPort2(NetworkPort aPort2) {
		thePort2 = aPort2;
	}
	/**
	 * 2-й порт
	 */
	private NetworkPort thePort2;
	/**
	 * Длина соединения в метрах
	 */
	@Comment("Длина соединения в метрах")
	
	public BigDecimal getLinkLength() {
		return theLinkLength;
	}
	public void setLinkLength(BigDecimal aLinkLength) {
		theLinkLength = aLinkLength;
	}
	/**
	 * Длина соединения в метрах
	 */
	private BigDecimal theLinkLength;
	/**
	 * Фабричное
	 */
	@Comment("Фабричное")
	
	public Boolean getFactory() {
		return theFactory;
	}
	public void setFactory(Boolean aFactory) {
		theFactory = aFactory;
	}
	/**
	 * Фабричное
	 */
	private Boolean theFactory;
}
