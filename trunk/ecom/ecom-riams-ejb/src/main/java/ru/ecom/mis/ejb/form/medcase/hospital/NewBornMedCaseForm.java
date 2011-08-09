package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.medcase.NewBornMedCase;
import ru.ecom.mis.ejb.form.birth.NewBornForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.NewBornMedCaseCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Случай стационарного лечения в отделении")
@EntityForm
@EntityFormPersistance(clazz=NewBornMedCase.class)
@WebTrail(comment = "Случай стационарного лечения в отделении по новорожденному", nameProperties= "id", view="entityParentView-stac_newBornSlo.do")
@Parent(property="parent", parentForm= NewBornForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/NewBornSlo")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(NewBornMedCaseCreateInterceptor.class)
)
public class NewBornMedCaseForm extends DepartmentMedCaseForm {
	/** Случай мед.обслуживания новорожденного */
	@Comment("Случай мед.обслуживания новорожденного")
	@Persist
	public Long getNewBorn() {return theNewBorn;}
	public void setNewBorn(Long aNewBorn) {theNewBorn = aNewBorn;}

	/** Случай мед.обслуживания новорожденного */
	private Long theNewBorn;

}
