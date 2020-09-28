package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocOmcMedServiceCost;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = VocOmcMedServiceCost.class)
@Comment("Цена по ОМС услуги")
@WebTrail(comment = "Цена по ОМС услуги", nameProperties = "id", view = "entityView-e2_vocMedServiceCost.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocOmcMedServiceCostForm extends IdEntityForm {
    /** Услуга */
    @Comment("Услуга")
    @Persist
    @Required
    public Long getMedService() {return theMedService;}
    public void setMedService(Long aMedService) {theMedService = aMedService;}
    private Long theMedService ;

    /** Цена */
    @Comment("Цена")
    @Persist
    @Required
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}
    private String theCost ;

    /** Дата начала действия цены */
    @Comment("Дата начала действия цены")
    @Persist
    @Required @DateString @DoDateString
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
    private String theStartDate ;

    /** Дата окончания действия цены */
    @Comment("Дата окончания действия цены")
    @Persist
    @DateString @DoDateString
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
    private String theFinishDate ;

    /** Рабочая функция врача по умолчанию */
    @Comment("Рабочая функция врача по умолчанию")
    @Persist
    public Long getWorkFunction() {return theWorkFunction;}
    public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
    private Long theWorkFunction ;
}
