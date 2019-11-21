package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.workcalendar.PlanOphtHospital;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.workcalendar.interceptor.PlanOphtHospitalCreate;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 06.11.2019.
 */
@EntityForm
@EntityFormPersistance(clazz = PlanOphtHospital.class)
@Comment("Планирование введения ингибиторов ангиогенеза")
@Parent(property="visit", parentForm=MedCaseForm.class)
@WebTrail(comment = "Планирование введения ингибиторов ангиогенеза", nameProperties= "id"
        ,list="entityParentList-stac_planOphtHospitalByVisit.do"
        , view="entityView-stac_planOphtHospitalByVisit.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(PlanOphtHospitalCreate.class)
)
public class PlanOphtHospitalByVisitForm extends PlanOphtHospitalForm {
}