package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.LaserDrive;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = LaserDrive.class)
@Comment("Лазерный дисковод")
@WebTrail(comment = "Лазерный дисковод", nameProperties= "id", list="entityParentList-asset_laserDrive.do", view="entityParentView-asset_laserDrive.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class LaserDriveForm extends ComponentForm{
	/**
	 * Тип дисковода
	 */
	@Comment("Тип дисковода")
	@Persist
	public Long getDriveType() {
		return theDriveType;
	}
	public void setDriveType(Long aDriveType) {
		theDriveType = aDriveType;
	}
	/**
	 * Тип дисковода
	 */
	private Long theDriveType;
}
