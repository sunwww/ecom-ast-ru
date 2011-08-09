package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.NetworkPoint;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = NetworkPoint.class)
@Comment("Сетевая точка")
@WebTrail(comment = "Сетевая точка", nameProperties= "id", list="entityParentList-asset_networkPoint.do", view="entityParentView-asset_networkPoint.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class NetworkPointForm extends IdEntityForm{
	/**
	 * Номер сетевой точки
	 */
	@Comment("Номер сетевой точки")
	@Persist
	public String getPointNumber() {
		return thePointNumber;
	}
	public void setPointNumber(String aPointNumber) {
		thePointNumber = aPointNumber;
	}
	/**
	 * Номер сетевой точки
	 */
	private String thePointNumber;
	/**
	 * Помещение
	 */
	@Comment("Помещение")
	@Persist
	public Long getRoom() {
		return theRoom;
	}
	public void setRoom(Long aRoom) {
		theRoom = aRoom;
	}
	/**
	 * Помещение
	 */
	private Long theRoom;
}
