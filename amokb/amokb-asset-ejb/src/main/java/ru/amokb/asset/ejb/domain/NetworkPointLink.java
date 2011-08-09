package ru.amokb.asset.ejb.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Соединение с сетевой точкой
	 */
	@Comment("Соединение с сетевой точкой")
@Entity
@Table(schema="SQLUser")
public class NetworkPointLink extends BaseEntity{
	/**
	 * Сетевая точка
	 */
	@Comment("Сетевая точка")
	@OneToOne
	public NetworkPoint getNetworkPoint() {
		return theNetworkPoint;
	}
	public void setNetworkPoint(NetworkPoint aNetworkPoint) {
		theNetworkPoint = aNetworkPoint;
	}
	/**
	 * Сетевая точка
	 */
	private NetworkPoint theNetworkPoint;
	/**
	 * Оборудование
	 */
	@Comment("Оборудование")
	@ManyToOne
	public Equipment getEquipment() {
		return theEquipment;
	}
	public void setEquipment(Equipment aEquipment) {
		theEquipment = aEquipment;
	}
	/**
	 * Оборудование
	 */
	private Equipment theEquipment;
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
