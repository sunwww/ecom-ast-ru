package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.NetworkLine;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = NetworkLine.class)
@Comment("Сетевая линия")
@WebTrail(comment = "Сетевая линия", nameProperties= "id", list="entityParentList-asset_networkLine.do", view="entityParentView-asset_networkLine.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class NetworkLineForm extends IdEntityForm{
	/**
	 * Сетевая точка
	 */
	@Comment("Сетевая точка")
	@Persist
	public Long getNetworkPoint() {
		return theNetworkPoint;
	}
	public void setNetworkPoint(Long aNetworkPoint) {
		theNetworkPoint = aNetworkPoint;
	}
	/**
	 * Сетевая точка
	 */
	private Long theNetworkPoint;
	/**
	 * Сетевой порт
	 */
	@Comment("Сетевой порт")
	@Persist
	public Long getNetworkPort() {
		return theNetworkPort;
	}
	public void setNetworkPort(Long aNetworkPort) {
		theNetworkPort = aNetworkPort;
	}
	/**
	 * Сетевой порт
	 */
	private Long theNetworkPort;
	/**
	 * Длина линиии в метрах
	 */
	@Comment("Длина линиии в метрах")
	@Persist
	public String getLineLength() {
		return theLineLength;
	}
	public void setLineLength(String aLineLength) {
		theLineLength = aLineLength;
	}
	/**
	 * Длина линиии в метрах
	 */
	private String theLineLength;
}
