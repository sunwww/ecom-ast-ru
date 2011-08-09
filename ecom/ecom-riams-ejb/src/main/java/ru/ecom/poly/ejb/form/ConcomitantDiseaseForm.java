package ru.ecom.poly.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.poly.ejb.domain.ConcomitantDisease;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 15.01.2007
 * Time: 21:07:09
 * To change this template use File | Settings | File Templates.
 */
@EntityForm
@EntityFormPersistance(clazz = ConcomitantDisease.class)
@Comment("Сопутствующие болезни")
@WebTrail(comment = "Сопутствующие болезни", nameProperties = "ticketInfo", view = "entityView-concomitantDisease.do")
@EntityFormSecurityPrefix("/Policy/Poly/ConcomitantDisease")
@Parent(property = "ticket", parentForm = TicketForm.class)
public class ConcomitantDiseaseForm extends IdEntityForm {
    /** @return Диагноз **/
    @Persist
    @Comment("Диагноз")
    public Long getDiagnosis() { return theDiagnosis; }
    public void setDiagnosis(Long aDiagnosis) { theDiagnosis = aDiagnosis; }

    /** @return Талон **/
    @Persist
    @Comment("Талон")
    public Long getTicket() { return theTicket; }
    public void setTicket(Long aTicket) { theTicket = aTicket; }

    /** @return Диагноз **/
    @Persist
    @Comment("Название диагноза")
    public String getDiagnosisName() { return theDiagnosisName; }
    public void setDiagnosisName(String aDiagnosisName) { theDiagnosisName = aDiagnosisName; }

    /** Диагноз **/
    private String theDiagnosisName;

    /** Талон **/
    private Long theTicket;

    /** Диагноз **/
    private Long theDiagnosis;
}
