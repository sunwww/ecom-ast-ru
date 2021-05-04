package ru.ecom.mis.ejb.form.extdisp;

import lombok.Setter;
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
@Parent(property="card", parentForm=ExtDispCardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Service")
@Setter
public class ExtDispExamForm extends ExtDispServiceForm {
	/**
	 * Выявлена патология
	 */
	@Comment("Выявлена патология")
	@Persist
	public Boolean getIsPathology() {
		return isPathology;
	}
	/**
	 * Выявлена патология
	 */
	private Boolean isPathology;
}
