package ru.ecom.mis.ejb.form.patient;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.IdentityCardPassport;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Паспорт
 */
@EntityForm
@EntityFormPersistance(clazz = IdentityCardPassport.class)
@Comment("Паспорт")
@WebTrail(comment = "Паспорт", nameProperties = "text", view = "entityParentView-mis_identityCardPassport.do")
@Parent(property = "patient", parentForm =PatientForm.class)
public class IdentityCardPassportForm extends IdentityCardForm {

    /** Серия */
    @Persist
    @Required
    public String getSeries() { return theSeries ; }
    public void setSeries(String aSeries) { theSeries = aSeries ; }

    /** Серия */
    private String theSeries ;

    /** Дата выдачи */
    @Persist
    @Required @DateString
    public String getDateIssue() { return theDateIssue ; }
    public void setDateIssue(String aDateIssue) { theDateIssue = aDateIssue ; }

    /** Кто выдал */
    @Persist
    @Required
    public Long getWhomIssue() { return theWhomIssue ; }
    public void setWhomIssue(Long aWhomIssue) { theWhomIssue = aWhomIssue ; }

    /** Кто выдал */
    private Long theWhomIssue ;
    /** Дата выдачи */
    private String theDateIssue ;
//    /** Номер */
//    private String theNumber ;
}
