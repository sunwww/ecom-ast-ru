package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.Staff;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz= Staff.class)
@Comment("Штатное расписание")
@WebTrail(comment = "Штатное расписание", nameProperties= "namePost", view="entityView-mis_stateList.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/StateList")
public class StateListForm extends IdEntityForm{
    /** ЛПУ */
    @Persist
    public Long getLpu() { return theLpu ; }
    public void setLpu(Long aLpu) { theLpu = aLpu ; }

    /** Должность */
    @Persist
    @Comment("Должность")
    @Required
    public Long getPost() { return thePost ;}
    public void setPost(Long aPost) { thePost = aPost ; }

    /** Наименование должности */
    @Persist
    public String getNamePost() { return theNamePost ; }
    public void setNamePost(String aNamePost) { theNamePost=aNamePost ; }

    /** Ставок всего */
    @Persist
    @Comment("Ставок всего")
    public Integer getFullRate() { return theFullRate ;}
    public void setFullRate(Integer aFullRate) { theFullRate = aFullRate ; }

    /** Ставок бюджетных */
    @Persist
    @Comment("Ставок бюджетных")
    public Integer getBudjetRate() {    return theBudjetRate ;}
    public void setBudjetRate(Integer aBudjetRate ) {  theBudjetRate = aBudjetRate ; }

    /** Ставок внебюджетных */
    @Persist
    @Comment("Ставок внебюджетных")
    public Integer getOffBudjetRate() {    return theOffBudjetRate ;}
    public void setOffBudjetRate(Integer aOffBudjetRate ) {  theOffBudjetRate = aOffBudjetRate ; }

    /** Ставок свободных всего */
    @Persist
    @Comment("Ставок свободных всего")
    public Integer getFreeFullRate() {    return theFreeFullRate ;}
    public void setFreeFullRate(Integer aFreeFullRate ) {  theFreeFullRate = aFreeFullRate ; }

    /** Ставок свободных бюджетных */
    @Persist
    @Comment("Ставок свободных бюджетных")
    public Integer getFreeBudjetRate() {    return theFreeBudjetRate ;}
    public void setFreeBudjetRate(Integer aFreeBudjetRate ) {  theFreeBudjetRate = aFreeBudjetRate ; }

    /** Ставок свободных внебюджетных */
    @Persist
    @Comment("Ставок свободных внебюджетных")
    public Integer getFreeOffBudjetRate() {    return theFreeOffBudjetRate ;}
    public void setFreeOffBudjetRate(Integer aFreeOffBudjetRate ) {  theFreeOffBudjetRate = aFreeOffBudjetRate ; }

    /** Ставок свободных внебюджетных */
    private Integer theFreeOffBudjetRate ;
    /** Ставок свободных бюджетных */
    private Integer theFreeBudjetRate ;
    /** Ставок свободных всего */
    private Integer theFreeFullRate ;
    /** Ставок внебюджетных */
    private Integer theOffBudjetRate ;
    /** Ставок бюджетных */
    private Integer theBudjetRate ;
    /** Ставок всего */
    private Integer theFullRate ;
    /** Наименование должности */
    private String theNamePost ;
    /** Должность */
    private Long thePost ;
    /** ЛПУ */
    private Long theLpu ;

}
