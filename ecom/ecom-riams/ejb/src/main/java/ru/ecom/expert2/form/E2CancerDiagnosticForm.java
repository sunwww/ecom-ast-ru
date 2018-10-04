package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2CancerDiagnostic;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = E2CancerDiagnostic.class)
@Comment("Диагностический блок")
@WebTrail(comment = "Диагностический блок", nameProperties = "id", view = "entityView-e2_cancerDiagnostic.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "cancerEntry", parentForm = E2CancerEntryForm.class)
public class E2CancerDiagnosticForm extends IdEntityForm {
    /** Случай рака */
    @Comment("Случай рака")
    @Persist
    public Long getCancerEntry() {return theCancerEntry;}
    public void setCancerEntry(Long aCancerEntry) {theCancerEntry = aCancerEntry;}
    /** Случай рака */
    private Long theCancerEntry ;

    /** Тип показателя */
    @Comment("Тип показателя")
    @Persist
    public String getType() {return theType;}
    public void setType(String aType) {theType = aType;}
    /** Тип показателя */
    private String theType ;

    /** Код показателя */
    @Comment("Код показателя")
    @Persist
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код показателя */
    private String theCode ;

    /** Результат показателя */
    @Comment("Результат показателя")
    @Persist
    public String getResult() {return theResult;}
    public void setResult(String aResult) {theResult = aResult;}
    /** Результат показателя */
    private String theResult ;
}
