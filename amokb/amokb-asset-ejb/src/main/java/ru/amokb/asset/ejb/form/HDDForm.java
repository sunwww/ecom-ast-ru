package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.HDD;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = HDD.class)
@Comment("Жесткий диск")
@WebTrail(comment = "Жесткий диск", nameProperties= "id", list="entityParentList-asset_hDD.do", view="entityParentView-asset_hDD.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class HDDForm extends ComponentForm{
	/**
	 * Емкость в ГБ
	 */
	@Comment("Емкость в ГБ")
	@Persist
	public String getStorage() {
		return theStorage;
	}
	public void setStorage(String aStorage) {
		theStorage = aStorage;
	}
	/**
	 * Емкость в ГБ
	 */
	private String theStorage;
	/**
	 * Тип жесткого диска
	 */
	@Comment("Тип жесткого диска")
	@Persist
	public Long getHddType() {
		return theHddType;
	}
	public void setHddType(Long aHddType) {
		theHddType = aHddType;
	}
	/**
	 * Тип жесткого диска
	 */
	private Long theHddType;
}
