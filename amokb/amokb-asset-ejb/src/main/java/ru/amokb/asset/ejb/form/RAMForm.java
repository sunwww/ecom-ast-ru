package ru.amokb.asset.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.amokb.asset.ejb.domain.RAM;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.validators.IntegerString;

@EntityForm
@EntityFormPersistance(clazz = RAM.class)
@Comment("Оперативная память")
@WebTrail(comment = "Оперативная память", nameProperties= "id", list="entityParentList-asset_rAM.do", view="entityParentView-asset_rAM.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class RAMForm extends ComponentForm{
	/**
	 * Тип оперативной памяти
	 */
	@Comment("Тип оперативной памяти")
	@Persist
	public Long getRamType() {
		return theRamType;
	}
	public void setRamType(Long aRamType) {
		theRamType = aRamType;
	}
	/**
	 * Тип оперативной памяти
	 */
	private Long theRamType;
	/**
	 * Емкость в МБ
	 */
	@Comment("Емкость в МБ")
	@Persist
	public int getStorage() {
		return theStorage;
	}
	public void setStorage(int aStorage) {
		theStorage = aStorage;
	}
	/**
	 * Емкость в МБ
	 */
	private int theStorage;
	/**
	 * Частота шины в МГц
	 */
	@Comment("Частота шины в МГц")
	@Persist
	public int getBusRate() {
		return theBusRate;
	}
	public void setBusRate(int aBusRate) {
		theBusRate = aBusRate;
	}
	/**
	 * Частота шины в МГц
	 */
	private int theBusRate;
}
