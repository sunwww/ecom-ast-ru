package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.Information;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Information.class)
@Comment("Информация")
@WebTrail(comment = "Информация", nameProperties= "id", list="entityParentList-asset_information.do", view="entityParentView-asset_information.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class InformationForm extends IdEntityForm{
	/**
	 * Место хранения
	 */
	@Comment("Место хранения")
	@Persist
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
	@Persist
	public Long getSecurityMark() {
		return theSecurityMark;
	}
	public void setSecurityMark(Long aSecurityMark) {
		theSecurityMark = aSecurityMark;
	}
	/**
	 * Метка безопасности
	 */
	private Long theSecurityMark;
}
