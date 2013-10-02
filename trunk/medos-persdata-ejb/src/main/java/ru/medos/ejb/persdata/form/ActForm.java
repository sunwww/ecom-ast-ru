package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.Act;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = Act.class)
@Comment("Акт")
@WebTrail(comment = "Акт", nameProperties= "id", list="entityParentList-personaldata_act.do", view="entityParentView-personaldata_act.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ActForm extends JournalDataForm{
	/**
	 * Номер акта
	 */
	@Comment("Номер акта")
	@Persist
	public String getActNumber() {
		return theActNumber;
	}
	public void setActNumber(String aActNumber) {
		theActNumber = aActNumber;
	}
	/**
	 * Номер акта
	 */
	private String theActNumber;
}
