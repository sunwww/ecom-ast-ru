package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.domain.medcase.Bandage;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.MedicalManipulationCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.MedicalManipulationViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;



/**
 * Created by Milamesher on 31.08.2017.
 */
@Comment("Перевязка")
@EntityForm
@EntityFormPersistance(clazz= Bandage.class)
@WebTrail(comment = "Перевязка", nameProperties= "id", view="entityParentView-stac_bandage.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Bandage")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedicalManipulationCreateInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(MedicalManipulationViewInterceptor.class)
)
public class BandageForm extends MedicalManipulationForm {}