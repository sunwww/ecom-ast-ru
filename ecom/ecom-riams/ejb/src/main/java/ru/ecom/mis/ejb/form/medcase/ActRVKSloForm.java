package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.mis.ejb.domain.medcase.ActRVK;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 05.12.2019.
 */
@Comment("Акт РВК стационар")
@EntityForm
@EntityFormPersistance(clazz= ActRVK.class)
@WebTrail(comment = "Акт РВК стационар", nameProperties= "id", view="entityParentView-rvk_aktVisit.do")
@Parent(property="medCase", parentForm= DepartmentMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/ActRVK")
@ACreateInterceptors(
        @AEntityFormInterceptor(ActRVKCreateInterceptor.class)
)
public class ActRVKSloForm extends ActRVKVisitForm {
}