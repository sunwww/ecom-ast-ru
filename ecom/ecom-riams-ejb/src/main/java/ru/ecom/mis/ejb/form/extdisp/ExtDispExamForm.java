package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispExam;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExtDispExam.class)
@Comment("Исследование по дополнительной диспансеризации")
@WebTrail(comment = "Исследование по дополнительной диспансеризации", nameProperties= "id", list="entityParentList-extdisp_extDispExam.do", view="entityParentView-extdisp_extDispExam.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class ExtDispExamForm extends IdEntityForm{
	/**
	 * Выявлена патология
	 */
	@Comment("Выявлена патология")
	@Persist
	public Boolean getIsPathology() {
		return theIsPathology;
	}
	public void setIsPathology(Boolean aIsPathology) {
		theIsPathology = aIsPathology;
	}
	/**
	 * Выявлена патология
	 */
	private Boolean theIsPathology;
}
