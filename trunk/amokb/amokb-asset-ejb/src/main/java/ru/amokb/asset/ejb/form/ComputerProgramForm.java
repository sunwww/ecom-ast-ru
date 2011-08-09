package ru.amokb.asset.ejb.form;

import ru.amokb.asset.ejb.domain.ComputerProgram;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = ComputerProgram.class)
@Comment("Программа компьютера")
@WebTrail(comment = "Программа компьютера", nameProperties= "id", list="entityParentList-asset_computerProgramm.do", view="entityParentView-asset_computerProgramm.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ComputerProgramForm extends IdEntityForm{
	/**
	 * Программа
	 */
	@Comment("Программа")
	@Persist
	public Long getProgramm() {
		return theProgramm;
	}
	public void setProgramm(Long aProgramm) {
		theProgramm = aProgramm;
	}
	/**
	 * Программа
	 */
	private Long theProgramm;
	/**
	 * 
	 */
	@Comment("")
	@Persist
	public Long get() {
		return the;
	}
	public void set(Long a) {
		the = a;
	}
	/**
	 * 
	 */
	private Long the;
	/**
	 * Дата установки
	 */
	@Comment("Дата установки")
	@Persist
	@DateString @DoDateString
	public String getEntranceDate() {
		return theEntranceDate;
	}
	public void setEntranceDate(String aEntranceDate) {
		theEntranceDate = aEntranceDate;
	}
	/**
	 * Дата установки
	 */
	private String theEntranceDate;
	/**
	 * Дата удаления
	 */
	@Comment("Дата удаления")
	@Persist
	@DateString @DoDateString
	public String getRemoveDate() {
		return theRemoveDate;
	}
	public void setRemoveDate(String aRemoveDate) {
		theRemoveDate = aRemoveDate;
	}
	/**
	 * Дата удаления
	 */
	private String theRemoveDate;
}
