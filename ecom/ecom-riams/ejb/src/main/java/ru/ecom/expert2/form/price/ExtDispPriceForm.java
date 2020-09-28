package ru.ecom.expert2.form.price;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.price.ExtDispPrice;
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
@EntityFormPersistance(clazz = ExtDispPrice.class)
@Comment("Цена ДД")
@WebTrail(comment = "Цена ДД", nameProperties = "id", view = "entityView-e2_extDispPrice.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class ExtDispPriceForm extends IdEntityForm {

    /** Тип диспансеризации */
    @Comment("Тип диспансеризации")
    @Persist @Required
    public Long getDispType() {return theDispType;}
    public void setDispType(Long aDispType) {theDispType = aDispType;}
    /** Тип диспансеризации */
    private Long theDispType ;

    /** Пол */
    @Comment("Пол")
    @Persist
    public Long getSex() {return theSex;}
    public void setSex(Long aSex) {theSex = aSex;}
    /** Пол */
    private Long theSex ;

    /** Цена полного случая */
    @Comment("Цена полного случая")
    @Persist @Required
    public String getCost() {return theCost;}
    public void setCost(String aCost) {theCost = aCost;}
    /** Цена полного случая */
    private String theCost ;

    /** Возраст с (мес) */
    @Comment("Возраст с (мес)")
    @Persist
    public Integer getAgeFrom() {return theAgeFrom;}
    public void setAgeFrom(Integer aAgeFrom) {theAgeFrom = aAgeFrom;}
    private Integer theAgeFrom ;

    /** Возраст по (мес) */
    @Comment("Возраст по (мес)")
    @Persist
    public Integer getAgeTo() {return theAgeTo;}
    public void setAgeTo(Integer aAgeTo) {theAgeTo = aAgeTo;}
    private Integer theAgeTo ;


    /** Ценовая (социальная) группа */
    @Comment("Ценовая (социальная) группа")
    @Persist
    public Integer getAgeGroup() {return theAgeGroup;}
    public void setAgeGroup(Integer aAgeGroup) {theAgeGroup = aAgeGroup;}
    /** Ценовая (социальная) группа */
    private Integer theAgeGroup ;

    /** Дата начала действия цены */
    @Comment("Дата начала действия цены")
    @Persist @Required
    @DateString @DoDateString
    public String getDateFrom() {return theDateFrom;}
    public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}
    /** Дата начала действия цены */
    private String theDateFrom ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    @Persist
    @DateString @DoDateString
    public String getDateTo() {return theDateTo;}
    public void setDateTo(String aDateTo) {theDateTo = aDateTo;}
    /** Дата окончания действия */
    private String theDateTo ;

    /** Возраста  */
    @Comment("Возраста ")
    @Persist
    public String getAges() {return theAges;}
    public void setAges(String aAges) {theAges = aAges;}
    /** Возраста  */
    private String theAges ;

    /** Минимальное кол-во услуг */
    @Comment("Минимальное кол-во услуг")
    @Persist @Required
    public Integer getMinServices() {return theMinServices;}
    public void setMinServices(Integer aMinServices) {theMinServices = aMinServices;}
    private Integer theMinServices ;
}
