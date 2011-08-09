package ru.amokb.asset.ejb.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Сетевая линия
	 */
	@Comment("Сетевая линия")
@Entity
@Table(schema="SQLUser")
public class NetworkLine extends BaseEntity{
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
	 * Сетевой порт
	 */
	@Comment("Сетевой порт")
	@OneToOne
	public NetworkPort getNetworkPort() {
		return theNetworkPort;
	}
	public void setNetworkPort(NetworkPort aNetworkPort) {
		theNetworkPort = aNetworkPort;
	}
	/**
	 * Сетевой порт
	 */
	private NetworkPort theNetworkPort;
	/**
	 * Длина линиии в метрах
	 */
	@Comment("Длина линиии в метрах")
	
	public BigDecimal getLineLength() {
		return theLineLength;
	}
	public void setLineLength(BigDecimal aLineLength) {
		theLineLength = aLineLength;
	}
	/**
	 * Длина линиии в метрах
	 */
	private BigDecimal theLineLength;
}
