package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.IdentityCard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Удостоверение личности
 */
@EntityForm
@EntityFormPersistance(clazz = IdentityCard.class)
@Comment("Удостоверение личности")
@WebTrail(comment = "Удостоверение личности", nameProperties = "text", view = "entitySubclassView-mis_identityCard.do")
@Parent(property = "patient", parentForm =PatientForm.class)
@Subclasses({IdentityCardPassportForm.class, IdentityCardPensionCertificateForm.class})
public class IdentityCardForm extends IdEntityForm {

    /** Номер */
    @Comment("Номер")
    @Persist
    @Required @DoUpperCase
    public String getNumber() { return theNumber ; }
    public void setNumber(String aNumber) { theNumber = aNumber ; }


    /** Пациент */
//    @Comment("Пациент")
//    @Persist
//    public Long getPatient() { return thePatient ; }
//    public void setPatient(Long aPatient) { thePatient = aPatient ; }

    /** Текст документа */
    @Comment("Текст документа")
    @Persist
    public String getText() { return theText ; }
    public void setText(String aText) { theText = aText ; }

    /** Текст документа */
    private String theText ;
//    /** Пациент */
//    private Long thePatient ;
    /** Номер */
    private String theNumber ;

}
