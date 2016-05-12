package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.birth.ChildBirth;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= ChildBirth.class)
@Comment("Роды")
@WebTrail(comment = "Роды", nameProperties= "id", view="entityParentView-preg_childBirthForPregnancy.do")
@Parent(property="pregnancy", parentForm= PregnancyForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/ChildBirth")
public class ChildBirthForPregnancyForm extends ChildBirthForm {
	/** Госпитализация */
	@Comment("Госпитализация")
	@Persist @Required
	public Long getSls() {return theSls;}
	public void setSls(Long aSls) {theSls = aSls;}

	/** Госпитализация */
	private Long theSls;
}
